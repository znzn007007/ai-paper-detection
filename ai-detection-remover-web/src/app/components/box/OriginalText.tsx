import React, { useState, useRef } from "react";
import styles from "./commonButton.module.scss";
import request from "@/app/shared";
import Toast from "@/app/components/shared/Toast";

interface OriginalTextProps {
	setProcessedText: (text: string) => void;
	setLoading: (loading: boolean) => void;
	loading: boolean;
}

export const OriginalText: React.FC<OriginalTextProps> = ({setProcessedText, setLoading, loading}) => {
	const [text, setText] = useState("");
	const [overlayVisible, setOverlayVisible] = useState(true);
	const [isButtonDisabled, setIsButtonDisabled] = useState(true);
	const fileInputRef = useRef<HTMLInputElement>(null);
	const [toast, setToast] = useState({message: "", type: ""});

	const showToast = (message: string, type: string) => {
		setToast({message, type});
	};

	const handlePaste = () => {
		navigator.clipboard.readText().then((clipText) => {
			setText(clipText);
			setOverlayVisible(false);
		});
	};

	const handleTextChange = (newText: string) => {
		setText(newText);
		if (newText !== "") {
			setIsButtonDisabled(false);
			setOverlayVisible(false);
		} else {
			setIsButtonDisabled(true);
			setOverlayVisible(true);
		}
	};

	const handleFileChange = (e: any) => {

		let file = e.target.files[0];
		setOverlayVisible(false);
		handleFileUpload(file);
	};


	const handleFileUpload = (file: File) => {
		const formData = new FormData();
		formData.append("file", file);
		formData.append("model", "GPT_3_5_TURBO");

		setLoading(true);
		request.post("/detections/file", formData, {
			headers: {
				"Content-Type": "multipart/form-data",
			},
		})
			.then((value: CustomResponse) => {
				setProcessedText(value.body.processed);
				handleTextChange(value.body.original);
				setLoading(false);
			})
			.catch((error: any) => {
				console.error("Error uploading file:", error);
				setLoading(false);
			});
	};

	const clickInput = () => {
		if (fileInputRef.current) {
			fileInputRef.current.click();
		}
	};

	const handleHumanize = () => {
		setLoading(true);
		request.post("/detections/text", {content: text, model: "GPT_3_5_TURBO"})
			.then((response) => {
				setProcessedText(response.body);
				setLoading(false);
			})
			.catch((error) => {
				console.error("Error humanizing text:", error);
				setLoading(false);
			});
	};

	return (
		<div className="relative flex h-[640px] flex-col pb-4 md:h-[420px] 2xl:h-[480px]">
			<div className="h-0 flex-1 px-4 pt-4">
				<div className="w-full h-full relative flex-1 p-4 rounded bg-[#141414]">
                    <textarea
	                    className="w-full h-full outline-none text-gray-300 bg-[#141414] resize-none focus:outline-none"
	                    placeholder="Enter the text you want to humanize here"
	                    value={text}
	                    onChange={(e) => handleTextChange(e.target.value)}
	                    disabled={loading}
                    />
				</div>
				{overlayVisible && (
					<div
						className="absolute left-1/2 top-1/2 z-[3] flex -translate-x-1/2 -translate-y-1/2 items-center space-x-3 text-gray-300">
						<div
							className="group flex h-fit w-fit min-w-[160px] flex-col items-center justify-center space-y-2 rounded-lg bg-[#1C1C1D] p-[14px] text-center text-xs hover:cursor-pointer hover:bg-[#2C2C2E] hover:text-[#DEE8F9]"
							onClick={clickInput}
						>
							<input
								type="file"
								accept=".pdf"
								onChange={handleFileChange}
								style={{display: "none"}}
								ref={fileInputRef}
							/>
							<span className="i-com--copy h-7 w-7">
                                <img className="i-com--copy h-7 w-7" src="/file.svg" alt="" />
                            </span>
							<span>Upload File</span>
						</div>
						<div
							className="group flex h-fit w-fit min-w-[160px] flex-col items-center justify-center space-y-2 rounded-lg bg-[#1C1C1D] p-[14px] text-center text-xs hover:cursor-pointer hover:bg-[#2C2C2E] hover:text-[#DEE8F9]"
							onClick={handlePaste}>
							<input
								type="file"
								style={{display: "none"}}
							/>
							<span className="i-com--copy h-7 w-7">
                                <img className="i-com--copy h-7 w-7" src="/paste.svg" alt="" />
                            </span>
							<span>Paste Text</span>
						</div>
					</div>
				)}
			</div>
			<div className="relative">
				<div
					className="flex w-full flex-wrap gap-y-3 px-4 pt-3 md:flex-col md:items-start md:justify-start items-center justify-between text-gray-50">
					<div className="flex gap-x-3 gap-y-2 md:w-full">
						<button
							className={`${styles.button} ${isButtonDisabled || loading ? styles.disabled : styles.enabled}`}
							onClick={handleHumanize}
							disabled={isButtonDisabled || loading}
						>
							Humanize
						</button>
					</div>
				</div>
			</div>
			<Toast message={toast.message} type={toast.type} />
		</div>
	);
};

export default OriginalText;
