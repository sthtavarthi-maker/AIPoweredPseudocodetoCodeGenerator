import { useState } from "react";

import InputSection from "../components/Generator/InputSection";
import CodeBlock from "../components/Generator/CodeBlock";
import ActionButtons from "../components/Generator/ActionButtons";
import AnalysisCards from "../components/Generator/AnalysisCards";
import LoadingSpinner from "../components/Generator/LoadingSpinner";

import {
generateCode,
generateAnalysis
} from "../services/api";

import "../styles/generator/Generator.css";
import "../styles/generator/Responsive.css";

function Generator() {


const [language, setLanguage] = useState("Java");
const [optimization, setOptimization] = useState("Optimal");
const [pseudo, setPseudo] = useState("");
const [inputType, setInputType] = useState("description");

const [loadingCode, setLoadingCode] = useState(false);
const [loadingAnalysis, setLoadingAnalysis] = useState(false);

const [response, setResponse] = useState(null);


const handleGenerateCode = async () => {

    if (!pseudo.trim()) {
        alert(
            "Please enter a problem description or pseudocode."
        );
        return;
    }

    // =====================================================
    // RESET
    // =====================================================

    setResponse(null);

    setLoadingCode(true);
    setLoadingAnalysis(false);


    try {

        const request = {
            prompt: pseudo,
            language,
            inputType,
            approach: optimization
        };


        // =================================================
        // OLLAMA CALL #1
        // GENERATE CODE ONLY
        // =================================================

        console.log(
            "========== FRONTEND: CODE GENERATION =========="
        );

        const codeResult =
            await generateCode(request);


        const generatedCode =
            codeResult.code;


        // =================================================
        // IMPORTANT:
        // DISPLAY CODE IMMEDIATELY
        // =================================================

        setResponse({
            code: generatedCode,

            explanation: "",
            dryRun: "",
            timeComplexity: "",
            spaceComplexity: "",
            edgeCases: ""
        });

        setLoadingCode(false);


        // =================================================
        // OLLAMA CALL #2
        // GENERATE ANALYSIS
        // =================================================

        setLoadingAnalysis(true);


        console.log(
            "========== FRONTEND: ANALYSIS GENERATION =========="
        );


        const analysisRequest = {

            prompt: pseudo,

            language,

            inputType,

            approach: optimization,

            generatedCode: generatedCode
        };


        const analysisResult =
            await generateAnalysis(
                analysisRequest
            );


        // =================================================
        // UPDATE ONLY ANALYSIS
        // CODE REMAINS DISPLAYED
        // =================================================

        setResponse(previous => ({

            ...previous,

            explanation:
                analysisResult.explanation || "",

            dryRun:
                analysisResult.dryRun || "",

            timeComplexity:
                analysisResult.timeComplexity || "",

            spaceComplexity:
                analysisResult.spaceComplexity || "",

            edgeCases:
                analysisResult.edgeCases || ""
        }));


    } catch (error) {
    console.error("Generation Error:", error);

    const message =
        error.response?.data?.message ||
        error.response?.data?.error ||
        error.message ||
        "Failed to generate code or analysis.";

    alert(message);
} finally {

        setLoadingCode(false);
        setLoadingAnalysis(false);
    }
};


return (
    <>

        <InputSection

            language={language}
            setLanguage={setLanguage}

            optimization={optimization}
            setOptimization={setOptimization}

            pseudo={pseudo}
            setPseudo={setPseudo}

            inputType={inputType}
            setInputType={setInputType}

            generateCode={handleGenerateCode}
        />


        {/* =================================================
            CODE LOADING
        ================================================= */}

        {loadingCode && (
            <LoadingSpinner />
        )}


        {/* =================================================
            GENERATED CODE
        ================================================= */}

        {!loadingCode && response && (
            <>

                <CodeBlock
                    language={language}
                    generatedCode={response.code}
                />


                {/* =================================================
                    ACTION BUTTONS
                ================================================= */}

                <ActionButtons
                    code={response.code}
                    language={language}
                />


                {/* =================================================
                    ANALYSIS LOADING
                ================================================= */}

                {loadingAnalysis && (

                    <div className="analysis-loading">

                        <h3>
                            Generating Analysis...
                        </h3>

                        <p>
                            The code has been generated.
                            AI is now analyzing your
                            selected solution approach.
                        </p>

                    </div>
                )}


                {/* =================================================
                    ANALYSIS
                ================================================= */}

                {!loadingAnalysis &&
                    response.explanation && (

                        <AnalysisCards

                            explanation={
                                response.explanation
                            }

                            dryRun={
                                response.dryRun
                            }

                            timeComplexity={
                                response.timeComplexity
                            }

                            spaceComplexity={
                                response.spaceComplexity
                            }

                            edgeCases={
                                response.edgeCases
                            }

                        />
                    )}

            </>
        )}

    </>
);

}

export default Generator;
