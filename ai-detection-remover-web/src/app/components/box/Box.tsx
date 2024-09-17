"use client";
import { useEffect, useState } from "react";
import OriginalText from "@/app/components/box/OriginalText";
import ProcessedText from "@/app/components/box/ProcessedText";
import Progress from "@/app/components/box/Progress";

const HumanizeText = () => {
	const [processedText, setProcessedText] = useState("");
	const [showProgress, setShowProgress] = useState(false);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (loading) {
			setShowProgress(false);
		} else if (processedText !== "") {
			setShowProgress(true);
		}
	}, [processedText, loading]);

	return (
		<div className="bg-gray-900 relative overflow-hidden pb-[88px] md:pb-10 lg:pb-20">
			<div className="text-display-primary relative mx-auto max-w-[1400px] px-4 pt-14 md:pt-4 2xl:pt-6">
				<h1 className="mb-10 text-center text-6xl font-bold leading-tight text-white
                    md:mb-3 md:text-2xl lg:text-4xl 2xl:mb-6">
					Convert AI Text To Human-like Content
				</h1>
				<div className="grid gap-4">
					<div className="flex w-full flex-col rounded-lg border-[4px] border-[#303030] bg-[#141414]">
						<div className="relative grid grid-cols-2 gap-y-3">
							<div className="absolute left-0 top-1/2 w-full h-[1px] bg-[#303030]
                                md:left-1/2 md:top-0 md:h-full md:w-[1px] z-10" />
							<OriginalText
								setProcessedText={setProcessedText}
								setLoading={setLoading}
								loading={loading}
							/>
							<ProcessedText processedText={processedText} loading={loading} />
						</div>
						{showProgress && (
							<Progress processedText={processedText} showProgress={showProgress} />
						)}
					</div>
				</div>
			</div>
		</div>
	);
};

export default HumanizeText;
