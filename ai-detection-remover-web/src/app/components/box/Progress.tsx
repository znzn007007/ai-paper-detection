import React, { useEffect, useState } from "react";
import request from "@/app/shared";
import Image from "next/image";

interface ProgressProps {
	processedText: string;
	showProgress: boolean;
}

export const Progress: React.FC<ProgressProps> = ({processedText, showProgress}) => {
	const [progress, setProgress] = useState(0);
	const [checkStatus, setCheckStatus] = useState(false);

	useEffect(() => {
		if (!showProgress) return;

		let progressInterval: NodeJS.Timeout;
		const checkText = async () => {
			setProgress(0);
			setCheckStatus(false);

			progressInterval = setInterval(() => {
				setProgress((prev) => {
					if (prev >= 100) {
						clearInterval(progressInterval);
					}
					return Math.min(prev + 10, 100);
				});
			}, 200);

			try {
				const response = await request.post("/detections/duplication", {content: processedText});
				setCheckStatus(response.body);
			} catch (error) {
				console.error("Error humanizing text:", error);
				setCheckStatus(false);
			} finally {
				clearInterval(progressInterval);
			}
		};

		checkText();

		return () => {
			clearInterval(progressInterval);
			setProgress(0);
		};
	}, [processedText, showProgress]);

	const bar = () => {
		return (
			<>
				<div className="mt-3 text-xl font-semibold text-[#DEE8F9] md:text-lg">
					<div className="flex justify-center text-center">
						Checking
						<span className="loading loading-dots loading-sm"></span>
					</div>
				</div>
				<progress className="progress progress-primary w-full" value={progress} max="100"></progress>
			</>

		);
	};

	const result = () => {
		return (
			<div className="flex flex-col items-center justify-center text-center">
				<Image height={40} width={40} src="/successful.svg" alt="" />
				<div className="mt-3 text-xl font-semibold text-[#DEE8F9] md:text-lg">
					The output content is most likely human-written.
				</div>
			</div>
		);
	};

	return (
		<div className="bg-[#1C1C1D] px-8 py-6 md:px-4 md:py-4">
			{/*{bar()}*/}
			{showProgress && (checkStatus ? result() : bar())}
		</div>
	);
};

export default Progress;
