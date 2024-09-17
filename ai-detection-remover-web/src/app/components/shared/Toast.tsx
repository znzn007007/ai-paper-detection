import React, { useState, useEffect } from "react";

interface ToastProps {
	message: string;
	type: string;
	duration?: number;
}

const Toast: React.FC<ToastProps> = ({message, type, duration = 3000}) => {
	const [visible, setVisible] = useState(false);

	useEffect(() => {
		if (message) {
			setVisible(true);
			const timer = setTimeout(() => {
				setVisible(false);
			}, duration);
			return () => clearTimeout(timer);
		}
	}, [message, duration]);

	if (!visible) return null;

	return (
		<div className="toast toast-top toast-start">
			<div className={`alert alert-${type}`}>
				<span>{message}</span>
			</div>
		</div>
	);
};

export default Toast;
