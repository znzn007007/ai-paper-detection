import React from "react";
import { Loading } from "@/app/components/shared/Loading";
import Image from "next/image";

interface ProcessedTextProps {
	processedText: string;
	loading: boolean;
}

export const ProcessedText: React.FC<ProcessedTextProps> = ({processedText, loading}) => {

	const renderContent = () => {
		return (
			processedText == "" ? (
					<div className="flex items-center justify-center w-full h-full">
						<Image height={64} width={64} src="/empty.png" alt="" />
					</div>)
				: (<div>{processedText}</div>)
		);
	};
	return (
		<div className="relative h-[640px] p-4 md:h-[420px] 2xl:h-[480px] bg-[#141414] text-gray-300">
			{loading ?
				(
					<div className="flex items-center justify-center w-full h-full rounded-lg bg-[#141414]
					dark:bg-gray-800 dark:border-gray-700">
						<span className="loading loading-spinner text-primary loading-lg"></span>
					</div>
				) : renderContent()
			}
		</div>
	);
};

export default ProcessedText;
