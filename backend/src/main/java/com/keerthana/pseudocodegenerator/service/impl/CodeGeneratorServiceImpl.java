
package com.keerthana.pseudocodegenerator.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.keerthana.pseudocodegenerator.dto.AnalysisResponse;
import com.keerthana.pseudocodegenerator.dto.CodeOnlyResponse;
import com.keerthana.pseudocodegenerator.dto.CodeRequest;
import com.keerthana.pseudocodegenerator.dto.CodeResponse;
import com.keerthana.pseudocodegenerator.entity.User;
import com.keerthana.pseudocodegenerator.repository.UserRepository;
import com.keerthana.pseudocodegenerator.service.CodeGeneratorService;
import com.keerthana.pseudocodegenerator.service.HistoryService;
import com.keerthana.pseudocodegenerator.service.OllamaService;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    private final OllamaService ollamaService;
    private final HistoryService historyService;
    private final UserRepository userRepository;

    public CodeGeneratorServiceImpl(
            OllamaService ollamaService,
            HistoryService historyService,
            UserRepository userRepository
    ) {
        this.ollamaService = ollamaService;
        this.historyService = historyService;
        this.userRepository = userRepository;
    }

// ============================================================
// PROGRAMMING INPUT VALIDATION
// Prevent unrelated requests from reaching Ollama
// ============================================================

private void validateProgrammingInput(CodeRequest request) {

    if (request == null ||
            request.getPrompt() == null ||
            request.getPrompt().isBlank()) {

        throw new IllegalArgumentException(
                "Please provide a valid programming problem description or pseudocode related to code generation."
        );
    }

    String prompt = request.getPrompt().trim().toLowerCase();
    String inputType = request.getInputType() == null
            ? ""
            : request.getInputType().trim().toLowerCase();

    // --------------------------------------------------------
    // Explicitly unrelated requests
    // --------------------------------------------------------

    String[] unrelatedPatterns = {
            "generate an image",
            "generate image",
            "create an image",
            "make an image",
            "draw an image",
            "generate a picture",
            "create a picture",
            "draw a picture",
            "write a poem",
            "write a story",
            "tell me a joke",
            "create a logo",
            "make a logo",
            "write an essay",
            "what is the capital",
            "who is the president",
            "translate this",
            "translate the following"
    };

    for (String pattern : unrelatedPatterns) {

        if (prompt.contains(pattern)) {

            throw new IllegalArgumentException(
                    "Please provide a valid programming problem description or pseudocode related to code generation."
            );
        }
    }

    // --------------------------------------------------------
    // Programming-related indicators
    // --------------------------------------------------------

    String[] programmingKeywords = {
            "program",
            "code",
            "algorithm",
            "array",
            "string",
            "integer",
            "number",
            "input",
            "output",
            "variable",
            "loop",
            "for loop",
            "while loop",
            "if condition",
            "condition",
            "function",
            "method",
            "class",
            "object",
            "recursion",
            "recursive",
            "sort",
            "sorting",
            "search",
            "binary search",
            "linear search",
            "stack",
            "queue",
            "linked list",
            "tree",
            "graph",
            "matrix",
            "prime",
            "palindrome",
            "factorial",
            "fibonacci",
            "maximum",
            "minimum",
            "largest",
            "smallest",
            "duplicate",
            "frequency",
            "even",
            "odd",
            "input",
            "output"
            
    };

    boolean programmingRelated = false;

    for (String keyword : programmingKeywords) {

        if (prompt.contains(keyword)) {
            programmingRelated = true;
            break;
        }
    }

    // --------------------------------------------------------
    // Pseudocode structural indicators
    // --------------------------------------------------------

    boolean looksLikePseudocode =
            inputType.equals("pseudocode") &&
            (
                    prompt.contains("start") ||
                    prompt.contains("end") ||
                    prompt.contains("read") ||
                    prompt.contains("print") ||
                    prompt.contains("if") ||
                    prompt.contains("else") ||
                    prompt.contains("while") ||
                    prompt.contains("for")
            );

    // --------------------------------------------------------
    // Final validation
    // --------------------------------------------------------

    if (!programmingRelated && !looksLikePseudocode) {

        throw new IllegalArgumentException(
                "Please provide a valid programming problem description or pseudocode related to code generation."
        );
    }
}
    // ============================================================
    // EXISTING COMBINED GENERATION
    // CODE + ANALYSIS
    // ============================================================

    @Override
    public CodeResponse generateCode(CodeRequest request) {

        // 1. Generate source code using selected approach
        CodeOnlyResponse codeResponse =
                generateCodeOnly(request);

        // 2. Generate analysis using the SAME selected approach
        //    and the ACTUAL generated code
        AnalysisResponse analysisResponse =
                generateAnalysis(
                        request,
                        codeResponse.getCode()
                );

        // 3. Combine code and analysis
        CodeResponse response = new CodeResponse();

        response.setCode(
                codeResponse.getCode()
        );

        response.setExplanation(
                analysisResponse.getExplanation()
        );

        response.setDryRun(
                analysisResponse.getDryRun()
        );

        response.setTimeComplexity(
                analysisResponse.getTimeComplexity()
        );

        response.setSpaceComplexity(
                analysisResponse.getSpaceComplexity()
        );

        response.setEdgeCases(
                analysisResponse.getEdgeCases()
        );

        return response;
    }


    // ============================================================
    // CODE GENERATION
    // OLLAMA CALL #1
    // ============================================================

    @Override
    public CodeOnlyResponse generateCodeOnly(CodeRequest request) {
        // ========================================================
        // VALIDATE INPUT BEFORE OLLAMA CALL #1
        // ========================================================

        validateProgrammingInput(request);



        String approach =
                normalizeApproach(
                        request.getApproach()
                );

        String approachInstruction =
                getCodeApproachInstruction(
                        approach
                );

        /*
         * IMPORTANT:
         *
         * This is the CODE generation prompt.
         *
         * generatedCode MUST NOT be used here because
         * generatedCode does not exist yet.
         *
         * Ollama CALL #1 creates the generated code.
         */

        String prompt = """
                You are an expert competitive programmer and software engineer.

                IMPORTANT:
                The user has explicitly selected a solution approach.
                You MUST follow the selected approach exactly.

                SELECTED APPROACH:
                %s

                APPROACH REQUIREMENT:
                %s

                TASK:
                Generate the solution for the given programming problem.

                CRITICAL APPROACH RULES:
                - Do NOT choose a different approach.
                - Do NOT automatically optimize the solution.
                - Do NOT replace the selected approach with another algorithm.
                - The generated code must directly implement the selected approach.
                - The algorithm used in the code must match the selected approach.
                - The explanation generated later will also be based on this same approach.

                OUTPUT REQUIREMENT:
                Return ONLY executable source code.

                ABSOLUTELY DO NOT:
                - Explain the solution.
                - Describe the algorithm.
                - Add comments explaining the solution.
                - Add notes before or after the code.
                - Add markdown.
                - Add triple backticks.
                - Add phrases such as "This solution", "The algorithm", or "Explanation".

                The response must start directly with the first line of source code
                and end immediately after the last line of source code.

                Programming Language: %s

                Problem:
                %s
                """.formatted(
                approach,
                approachInstruction,
                request.getLanguage(),
                request.getPrompt()
        );

        System.out.println(
                "========== CODE OLLAMA START =========="
        );

        System.out.println(
                "Selected approach: " + approach
        );

        System.out.println(
                "Language: " + request.getLanguage()
        );

        int codeTokens =
                calculateTokenLimit(
                        request.getPrompt()
                );

        System.out.println(
                "Code token limit: " + codeTokens
        );

        // ========================================================
        // OLLAMA CALL #1
        // ========================================================

        String code =
                ollamaService.generateResponse(
                        prompt,
                        codeTokens
                );

        System.out.println(
                "========== CODE OLLAMA END =========="
        );

        // ========================================================
        // CLEAN ACCIDENTAL MARKDOWN
        // ========================================================

        if (code == null) {
            code = "";
        }

        code = code
                .replaceAll("```\\w*", "")
                .replace("```", "")
                .trim();

        // ========================================================
        // REMOVE ACCIDENTAL EXPLANATION
        // ========================================================

        int explanationIndex =
                code.indexOf(
                        "\nThis solution"
                );

        if (explanationIndex != -1) {

            code =
                    code.substring(
                            0,
                            explanationIndex
                    ).trim();
        }

        int algorithmIndex =
                code.indexOf(
                        "\nThe algorithm"
                );

        if (algorithmIndex != -1) {

            code =
                    code.substring(
                            0,
                            algorithmIndex
                    ).trim();
        }

        int explanationPhraseIndex =
                code.indexOf(
                        "\nExplanation:"
                );

        if (explanationPhraseIndex != -1) {

            code =
                    code.substring(
                            0,
                            explanationPhraseIndex
                    ).trim();
        }

        // ========================================================
        // SAVE GENERATED CODE TO HISTORY
        // ========================================================

        saveGeneratedHistory(
                request,
                code
        );

        return new CodeOnlyResponse(code);
    }


    // ============================================================
    // SAVE GENERATED HISTORY
    // ============================================================

    private void saveGeneratedHistory(
            CodeRequest request,
            String generatedCode
    ) {

        try {

            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            // ----------------------------------------------------
            // Make sure a logged-in user exists
            // ----------------------------------------------------

            if (authentication == null ||
                    !authentication.isAuthenticated() ||
                    authentication.getName() == null) {

                System.out.println(
                        "No authenticated user. History not saved."
                );

                return;
            }

            String email =
                    authentication.getName();

            User user =
                    userRepository.findByEmail(email)
                            .orElse(null);

            if (user == null) {

                System.out.println(
                        "Authenticated user not found. History not saved."
                );

                return;
            }

            // ----------------------------------------------------
            // Save COMPLETE generated code
            // ----------------------------------------------------

            historyService.saveHistory(
                    request.getLanguage(),
                    request.getInputType(),
                    request.getApproach(),
                    request.getPrompt(),
                    generatedCode,
                    user.getId()
            );

            System.out.println(
                    "History saved successfully for user: "
                            + email
            );

        } catch (Exception e) {

            /*
             * History failure must NOT break AI code generation.
             */

            System.err.println(
                    "Failed to save history: "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // APPROACH NORMALIZATION
    // ============================================================

    private String normalizeApproach(String approach) {

        if (approach == null || approach.isBlank()) {
            return "optimal";
        }

        String normalized =
                approach
                        .trim()
                        .toLowerCase();

        if (normalized.equals("brute")
                || normalized.equals("bruteforce")
                || normalized.equals("brute-force")
                || normalized.equals("brute force")) {

            return "brute";
        }

        if (normalized.equals("optimal")
                || normalized.equals("optimized")
                || normalized.equals("efficient")) {

            return "optimal";
        }

        // Unexpected value
        // Default to optimal
        return "optimal";
    }


    // ============================================================
    // CODE APPROACH INSTRUCTIONS
    // ============================================================

    private String getCodeApproachInstruction(
            String approach) {

        if ("brute".equals(approach)) {

            return """
                    Generate the BRUTE FORCE solution.

                    The solution must:
                    - Use the straightforward or exhaustive approach.
                    - Check possible candidates/combinations where appropriate.
                    - Prioritize simplicity and direct implementation.
                    - Do not replace the brute-force method with an optimized algorithm.
                    - Do not use advanced optimization techniques merely to improve complexity.
                    - The final code must represent the natural brute-force solution to the problem.
                    """;
        }

        return """
                Generate the OPTIMAL solution.

                The solution must:
                - Use the most efficient practical algorithm for the problem.
                - Improve unnecessary time complexity where possible.
                - Use appropriate data structures and algorithmic techniques.
                - Avoid unnecessary exhaustive enumeration when a better approach exists.
                - The final code must represent the optimized/efficient solution.
                """;
    }


    // ============================================================
    // DYNAMIC CODE TOKEN LIMIT
    // ============================================================

    /*
     * Dynamic token limit for CODE.
     *
     * <= 100 characters   -> 250 tokens
     * <= 250 characters   -> 400 tokens
     * <= 500 characters   -> 550 tokens
     * <= 800 characters   -> 700 tokens
     * <= 1200 characters  -> 850 tokens
     * > 1200 characters   -> 1000 tokens
     */

    private int calculateTokenLimit(
            String problem) {

        if (problem == null || problem.isBlank()) {
            return 250;
        }

        int length = problem.length();

        if (length <= 100) {
            return 250;
        }

        if (length <= 250) {
            return 400;
        }

        if (length <= 500) {
            return 550;
        }

        if (length <= 800) {
            return 700;
        }

        if (length <= 1200) {
            return 850;
        }

        return 1000;
    }


    // ============================================================
    // ANALYSIS TOKEN LIMIT
    // ============================================================

    private int calculateAnalysisTokenLimit(
            String problem) {

        if (problem == null || problem.isBlank()) {
            return 1000;
        }

        int length = problem.length();

        if (length <= 100) {
            return 1000;
        }

        if (length <= 250) {
            return 1200;
        }

        if (length <= 500) {
            return 1500;
        }

        if (length <= 800) {
            return 1800;
        }

        if (length <= 1200) {
            return 2200;
        }

        return 3000;
    }


    // ============================================================
    // ANALYSIS GENERATION
    // OLLAMA CALL #2
    // ============================================================

    @Override
    public AnalysisResponse generateAnalysis(
            CodeRequest request,
            String generatedCode) {

        String approach =
                normalizeApproach(
                        request.getApproach()
                );

        String approachInstruction =
                getAnalysisApproachInstruction(
                        approach
                );

        /*
         * IMPORTANT:
         *
         * generatedCode is used HERE.
         *
         * Ollama CALL #1 already generated the code.
         * Ollama CALL #2 now analyzes THAT EXACT CODE.
         */

       String prompt = """
        You are an expert programming instructor and code-analysis specialist.

        Your task is to analyze ONLY the ACTUAL GENERATED CODE provided below.

        IMPORTANT:
        The generated code is the single source of truth.
        Do not analyze an imagined or alternative solution.
        Do not optimize the code.
        Do not change the algorithm.
        Do not assume behavior that is not present in the code.

        SELECTED APPROACH:
        %s

        APPROACH REQUIREMENT:
        %s

        Return EXACTLY these five sections in this exact order:

        ===EXPLANATION===

        Explain the actual generated program in 2-3 sentences.

        Mention the actual:
        - input
        - important variables
        - data structures
        - loops
        - conditions
        - functions or methods

        Do not include a dry run in this section.
        Do not describe steps or example values in this section.


        ===DRY_RUN===

        Perform a REAL EXECUTION TRACE of the ACTUAL GENERATED CODE.

        This is NOT a general description of the algorithm.

        Use this concrete input unless it is incompatible with the generated program:
        Array size = 5
        Array = [12, 7, 25, 9, 18]

        IMPORTANT DRY-RUN RULES:

        1. Use actual values from the concrete input.
        2. Start from the actual initial variable values in the code.
        3. Follow the generated code in exact execution order.
        4. Track important variables after each operation.
        5. For maximum-element problems, explicitly track the current maximum.
        6. For loops, show important index/counter values.
        7. For conditions, evaluate the condition using the current values.
        8. If a variable changes, show its new value.
        9. If an array changes, show its current state.
        10. Do not write generic statements such as:
            "The program reads the input."
            "The loop iterates through the array."
            "The maximum is found."
        11. Do not describe what the program does without using actual values.
        12. Do not invent operations that are absent from the generated code.
        13. Do not use stale variable values.
        14. The final step must show the actual final output.

        Use EXACTLY 5 numbered steps.

        Each step must contain actual values.

        Example format:

        1. Input: size = 5, array = [12, 7, 25, 9, 18].
        2. Initialize max = 12. Compare 7 with 12; max remains 12.
        3. Compare 25 with 12; 25 is greater, so max becomes 25.
        4. Compare 9 and 18 with current max 25; max remains 25.
        5. The program prints 25. Final result = 25.

        The example above is only a formatting example.
        You MUST follow the actual generated code.


        ===TIME_COMPLEXITY===

        Determine the time complexity from the ACTUAL GENERATED CODE.

        Rules:
        - Inspect every loop.
        - Count loop nesting.
        - Consider recursion if present.
        - Consider operations performed on data structures.
        - Do not copy complexity from the problem description.
        - Do not assume O(n) without examining the code.

        Format exactly:

        Complexity: [Big-O]

        Reason:
        [Reason based on the actual generated code.]

        ===SPACE_COMPLEXITY===

        Determine the SPACE COMPLEXITY from the ACTUAL GENERATED CODE.

        THIS IS A CODE-BASED ANALYSIS. Do not determine space complexity from the
        problem statement or from the expected algorithm alone.

        IMPORTANT:

        1. Inspect the ACTUAL GENERATED CODE before determining the space complexity.

        2. Identify every variable, array, collection, object, string, matrix,
        recursive call, and dynamically allocated data structure created by the
        program.

        3. Determine whether the memory required by each structure grows with the
        input size n.

        4. Report TOTAL SPACE COMPLEXITY by default.

        Total space includes memory allocated by the generated program for input
        storage and other data structures created during execution.

        5. Do NOT report O(1) simply because the program uses only a few scalar
        variables.

        For example:

        int max;
        int i;
        int temp;

        These variables use O(1) memory, but they do NOT make the entire program
        O(1) if the program also allocates memory that grows with n.

        6. If the generated code contains:

        int[] array = new int[size];

        then the array requires O(n) memory.

        Therefore, the TOTAL SPACE COMPLEXITY is O(n), unless the actual code
        contains another structure that requires more memory.

        7. If the generated code stores n elements in a data structure, its memory
        usage is generally O(n).

        Examples:

        int[] array of n elements              -> O(n)
        String containing n characters         -> O(n)
        StringBuilder containing n characters -> O(n)
        ArrayList containing n elements        -> O(n)
        LinkedList containing n elements       -> O(n)
        HashSet containing n elements          -> O(n)
        HashMap containing n entries           -> O(n)
        Stack containing n elements            -> O(n)
        Queue containing n elements            -> O(n)

        8. If the generated code creates a two-dimensional structure containing
        n x n elements, report O(n^2).

        Example:

        int[][] matrix = new int[n][n];

        Space complexity -> O(n^2)

        9. If the generated code creates multiple structures, consider all of them.

        Example:

        int[] a = new int[n];
        int[] b = new int[n];

        Total space -> O(n)

        Do not incorrectly report O(1) because there are only two variables.
        The two arrays together require memory proportional to n.

        10. If the generated code creates a structure whose size depends on another
        input parameter, use the actual parameters.

        Example:

        int[][] matrix = new int[rows][cols];

        Space complexity -> O(rows * cols)

        Do not automatically change this to O(n^2) unless rows and cols are both
        defined in terms of n.

        11. For strings and character data, consider the number of characters stored.

        Example:

        String s = scanner.nextLine();

        If the input string can contain n characters, the stored string requires
        O(n) memory.

        12. For recursion, count the memory used by the recursive call stack.

        If the maximum recursion depth is n, the recursion stack requires O(n)
        space.

        If the recursion depth is logarithmic, report O(log n), provided the
        actual generated code supports that depth.

        13. Distinguish between temporary scalar variables and memory that grows with
        the input.

        A fixed number of variables such as:

        int i;
        int sum;
        int temp;

        requires O(1) memory.

        However, if the program also creates an array, list, map, string, matrix,
        recursion stack, or other structure that grows with n, include that
        memory in the TOTAL SPACE COMPLEXITY.

        14. Do not confuse TIME COMPLEXITY with SPACE COMPLEXITY.

        A loop that executes n times does not automatically require O(n) space.

        Example:

        for (int i = 0; i < n; i++) {
                sum += i;
        }

        If no memory grows with n, the space complexity is O(1).

        15. Do not assume that every input requires newly allocated memory.

        Inspect the actual code.

        If the program receives an existing value and only uses a fixed number of
        variables, report O(1) when no memory grows with n.

        16. If the code modifies an existing array in place and does not create
        another data structure whose size depends on n, distinguish the input
        array from newly allocated auxiliary memory.

        For TOTAL SPACE COMPLEXITY, include the input storage when the generated
        program itself allocates and stores that input.

        17. Do not automatically report auxiliary space complexity.

        Report TOTAL SPACE COMPLEXITY unless the user explicitly asks for
        "auxiliary space complexity" or "extra space".

        18. If the code allocates an input array such as:

        int[] array = new int[size];

        then report:

        Complexity: O(n)

        Reason:
        The generated program allocates an array containing n elements, so the
        memory required by the program grows linearly with the input size.

        19. If no data structure, object, string, recursion stack, or other memory
        allocation grows with the input size, report:

        Complexity: O(1)

        Reason:
        The generated program uses only a fixed amount of memory that does not
        increase as the input size grows.

        20. If multiple structures have different growth rates, report the dominant
        growth rate.

        Example:

        int[] array = new int[n];       -> O(n)
        int[][] matrix = new int[n][n]; -> O(n^2)

        Total space -> O(n^2)

        Do not add lower-order terms when reporting Big-O.

        FINAL VERIFICATION:

        Before producing the answer, verify the ACTUAL GENERATED CODE again.

        Check:

        - Which data structures are actually created?
        - How many elements can each structure contain?
        - Does its size grow with n?
        - Is there a recursive call stack?
        - Are there multiple dynamically allocated structures?
        - Is there a matrix or nested structure?
        - Are there strings or collections whose size grows with n?
        - Are only fixed-size scalar variables used?
        - What is the dominant memory growth?

        Never report O(1) when the actual generated code allocates memory that grows
        with the input size.

        OUTPUT FORMAT:

        Complexity: [Big-O space complexity]

        Reason:
        [Explain exactly which memory allocations in the ACTUAL GENERATED CODE
        determine the space complexity.]
        
        ===EDGE_CASES===

                Provide exactly 3 genuine edge cases for the ACTUAL GENERATED CODE.

                The edge cases MUST be derived from and verified against the actual generated
                code. Do not generate generic edge cases based only on the problem statement.

                IMPORTANT RULES:

                1. Read and analyze the ACTUAL GENERATED CODE before selecting edge cases.

                2. Each edge case must contain:
                - one concrete input
                - the actual execution performed by the generated code
                - the actual final result produced by the code

                3. Execute the code logically using the actual variables, initial values,
                conditions, loops, comparisons, assignments, and control flow present in
                the generated code.

                4. Follow loop initialization and loop conditions exactly.
                If the loop condition is false initially, explicitly state that the loop
                does not execute. Do not describe any comparison or operation that would
                occur inside that loop.

                5. Evaluate every important comparison using the actual values.
                Do not claim a condition is true unless the actual values make it true.
                Do not claim a condition is false unless the actual values make it false.

                6. Update variables ONLY when the corresponding condition in the generated
                code is actually true.

                7. Continue the execution until the actual result is reached. Do not stop
                before the relevant value, iteration, branch, or operation has been
                processed.

                8. Do not invent behavior that is not present in the generated code.

                9. Do not assume that an edge case is valid merely because it is common for
                the algorithm. Verify that the generated code can actually process that
                input.

                10. If the generated code accesses an element such as array[0], verify the
                behavior for an empty array before using it as an edge case. If the
                actual code would throw an exception, state the actual exception or
                failure behavior instead of claiming that the program prints nothing.

                11. If the generated code contains input validation, follow that validation
                exactly. Do not execute later statements when an earlier condition
                prevents them from executing.

                12. For array or loop-based programs, consider meaningful boundary cases such
                as:
                - minimum valid input size
                - repeated or equal values
                - important boundary positions
                - maximum/minimum values
                - values that trigger or do not trigger a condition
                Only use a case when it is relevant to the ACTUAL GENERATED CODE.

                13. For string, mathematical, searching, sorting, recursion, or other
                algorithms, select edge cases appropriate to the actual generated code.
                Do not force array-specific edge cases onto unrelated programs.

                14. Do not use the same execution scenario three times with minor wording
                changes. The three cases must represent three distinct and meaningful
                boundary or special conditions supported by the generated code.

                15. Do not number individual execution statements as separate edge cases.
                Each complete input, execution, and result must belong to one edge case.

                OUTPUT FORMAT — FOLLOW EXACTLY:

                Edge Case 1:
                Input: [concrete input]
                Execution: [actual execution using the real values and actual code logic]
                Result: [actual result produced by the code]

                Edge Case 2:
                Input: [concrete input]
                Execution: [actual execution using the real values and actual code logic]
                Result: [actual result produced by the code]

                Edge Case 3:
                Input: [concrete input]
                Execution: [actual execution using the real values and actual code logic]
                Result: [actual result produced by the code]

                FINAL VERIFICATION BEFORE RESPONDING:

                Before producing the EDGE_CASES section, verify all three cases against the
                actual generated code.

                - The input must match the code's expected input.
                - Every stated variable value must come from actual execution.
                - Every comparison must use the correct actual values.
                - Every loop execution must satisfy the actual loop condition.
                - Every variable update must satisfy the actual condition.
                - Every stated result must match the actual code.
                - Do not claim behavior that the generated code does not perform.
                - Do not omit an important iteration that affects the final result.
                - Do not describe hypothetical behavior.

                The edge cases must describe what the GENERATED CODE ACTUALLY DOES, not what
                the algorithm is generally expected to do.
                        

        
        Programming Language:
        %s

        Problem:
        %s

        GENERATED CODE:
        %s
        """.formatted(
        approach,
        approachInstruction,
        request.getLanguage(),
        request.getPrompt(),
        generatedCode
);

        System.out.println(
                "========== ANALYSIS OLLAMA START =========="
        );

        System.out.println(
                "Selected approach: " + approach
        );

        int analysisTokens =
                calculateAnalysisTokenLimit(
                        request.getPrompt()
                );

        System.out.println(
                "Analysis token limit: "
                        + analysisTokens
        );

        // ========================================================
        // OLLAMA CALL #2
        // ========================================================

        String analysis =
                ollamaService.generateResponse(
                        prompt,
                        analysisTokens
                );

        System.out.println(
                "========== ANALYSIS OLLAMA END =========="
        );

        System.out.println(
                "========== OLLAMA ANALYSIS =========="
        );

        System.out.println(analysis);

        System.out.println(
                "===================================="
        );

        return parseAnalysisResponse(
                analysis
        );
    }


    // ============================================================
    // ANALYSIS APPROACH INSTRUCTIONS
    // ============================================================

    private String getAnalysisApproachInstruction(
            String approach) {

        if ("brute".equals(approach)) {

            return """
                    Explain the straightforward BRUTE FORCE algorithm.

                    The explanation, dry run, time complexity, space complexity,
                    and edge cases must all correspond to the brute-force
                    implementation.

                    Analyze the actual generated brute-force code.

                    Do not optimize the algorithm during analysis.
                    Do not describe an optimal alternative.
                    """;
        }

        return """
                Explain the OPTIMAL algorithm.

                The explanation, dry run, time complexity, space complexity,
                and edge cases must all correspond to the optimized
                implementation.

                Analyze the actual generated optimal code.

                Do not describe a brute-force alternative.
                """;
    }


    // ============================================================
    // ANALYSIS RESPONSE PARSER
    // ============================================================
      
      private AnalysisResponse parseAnalysisResponse(
        String analysis) {

    AnalysisResponse response =
            new AnalysisResponse();

    if (analysis == null || analysis.isBlank()) {

        System.out.println(
                "ANALYSIS IS EMPTY"
        );

        return response;
    }

    System.out.println(
            "========== RAW ANALYSIS =========="
    );

    System.out.println(analysis);

    System.out.println(
            "=================================="
    );

    String normalized =
            analysis
                    .replace("\r\n", "\n")
                    .replace("\r", "\n")
                    .trim();

    /*
     * Normalize all possible heading variations produced by Ollama.
     *
     * Examples supported:
     *
     * ===EXPLANATION===
     * ## EXPLANATION
     * **EXPLANATION**
     * EXPLANATION:
     * === DRY_RUN ===
     * === DRY RUN ===
     * ## DRY RUN
     * ===TIME COMPLEXITY===
     * ## TIME COMPLEXITY
     */

    normalized = normalized
            .replaceAll(
                    "(?im)^\\s*(?:[#*]+\\s*)*=*\\s*EXPLANATION\\s*=*\\s*[:*]*\\s*$",
                    "===EXPLANATION==="
            )
            .replaceAll(
                    "(?im)^\\s*(?:[#*]+\\s*)*=*\\s*DRY\\s*[_ -]?\\s*RUN\\s*=*\\s*[:*]*\\s*$",
                    "===DRY_RUN==="
            )
            .replaceAll(
                    "(?im)^\\s*(?:[#*]+\\s*)*=*\\s*TIME\\s*[_ -]?\\s*COMPLEXITY\\s*=*\\s*[:*]*\\s*$",
                    "===TIME_COMPLEXITY==="
            )
            .replaceAll(
                    "(?im)^\\s*(?:[#*]+\\s*)*=*\\s*SPACE\\s*[_ -]?\\s*COMPLEXITY\\s*=*\\s*[:*]*\\s*$",
                    "===SPACE_COMPLEXITY==="
            )
            .replaceAll(
                    "(?im)^\\s*(?:[#*]+\\s*)*=*\\s*EDGE\\s*[_ -]?\\s*CASES\\s*=*\\s*[:*]*\\s*$",
                    "===EDGE_CASES==="
            );

    /*
     * Some models may return the required headings without
     * Markdown but with extra spaces inside the markers.
     */

    normalized = normalized
            .replaceAll(
                    "(?i)===\\s*EXPLANATION\\s*===",
                    "===EXPLANATION==="
            )
            .replaceAll(
                    "(?i)===\\s*DRY\\s*[_ -]?\\s*RUN\\s*===",
                    "===DRY_RUN==="
            )
            .replaceAll(
                    "(?i)===\\s*TIME\\s*[_ -]?\\s*COMPLEXITY\\s*===",
                    "===TIME_COMPLEXITY==="
            )
            .replaceAll(
                    "(?i)===\\s*SPACE\\s*[_ -]?\\s*COMPLEXITY\\s*===",
                    "===SPACE_COMPLEXITY==="
            )
            .replaceAll(
                    "(?i)===\\s*EDGE\\s*[_ -]?\\s*CASES\\s*===",
                    "===EDGE_CASES==="
            );

    System.out.println(
            "========== NORMALIZED ANALYSIS =========="
    );

    System.out.println(normalized);

    System.out.println(
            "========================================="
    );

    /*
     * Extract each section independently.
     *
     * Explanation:
     * ===EXPLANATION=== -> ===DRY_RUN===
     *
     * Dry Run:
     * ===DRY_RUN=== -> ===TIME_COMPLEXITY===
     *
     * Time:
     * ===TIME_COMPLEXITY=== -> ===SPACE_COMPLEXITY===
     *
     * Space:
     * ===SPACE_COMPLEXITY=== -> ===EDGE_CASES===
     *
     * Edge Cases:
     * ===EDGE_CASES=== -> end of response
     */

    String explanation =
            extractSection(
                    normalized,
                    "EXPLANATION",
                    "DRY_RUN"
            );

    String dryRun =
            extractSection(
                    normalized,
                    "DRY_RUN",
                    "TIME_COMPLEXITY"
            );

    String timeComplexity =
            extractSection(
                    normalized,
                    "TIME_COMPLEXITY",
                    "SPACE_COMPLEXITY"
            );

    String spaceComplexity =
            extractSection(
                    normalized,
                    "SPACE_COMPLEXITY",
                    "EDGE_CASES"
            );

    String edgeCases =
            extractSection(
                    normalized,
                    "EDGE_CASES",
                    null
            );

    response.setExplanation(
            explanation
    );

    response.setDryRun(
            dryRun
    );

    response.setTimeComplexity(
            timeComplexity
    );

    response.setSpaceComplexity(
            spaceComplexity
    );

    response.setEdgeCases(
            edgeCases
    );

    System.out.println(
            "========== PARSED VALUES =========="
    );

    System.out.println(
            "Explanation = ["
                    + response.getExplanation()
                    + "]"
    );

    System.out.println(
            "DryRun = ["
                    + response.getDryRun()
                    + "]"
    );

    System.out.println(
            "TimeComplexity = ["
                    + response.getTimeComplexity()
                    + "]"
    );

    System.out.println(
            "SpaceComplexity = ["
                    + response.getSpaceComplexity()
                    + "]"
    );

    System.out.println(
            "EdgeCases = ["
                    + response.getEdgeCases()
                    + "]"
    );

    System.out.println(
            "=================================="
    );

    return response;
}

    // ============================================================
    // SECTION EXTRACTION
    // ============================================================
   
   private String extractSection(
        String text,
        String start,
        String end) {

    if (text == null || text.isBlank()) {
        return "";
    }

    String startTag =
            "===" + start + "===";

    int startIndex =
            text.indexOf(startTag);

    System.out.println(
            "START: "
                    + startTag
                    + " -> "
                    + startIndex
    );

    if (startIndex == -1) {
        return "";
    }

    startIndex += startTag.length();

    int endIndex;

    if (end == null) {

        endIndex =
                text.length();

    } else {

        String endTag =
                "===" + end + "===";

        endIndex =
                text.indexOf(
                        endTag,
                        startIndex
                );

        System.out.println(
                "END: "
                        + endTag
                        + " -> "
                        + endIndex
        );

        if (endIndex == -1) {

            /*
             * If the next section is missing, take the
             * remaining content instead of losing it.
             */

            endIndex =
                    text.length();
        }
    }

    if (endIndex < startIndex) {
        return "";
    }

    return text
            .substring(
                    startIndex,
                    endIndex
            )
            .trim();
}
    
}
