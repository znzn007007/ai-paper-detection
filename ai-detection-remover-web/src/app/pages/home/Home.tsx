import styles from "./home.module.scss";
import Box from "@/app/components/box/Box";
import Head from "@/app/components/Header";
import Footer from "@/app/components/Footer";

export function Home() {
	return (
		<div>
			<Head />
			<Box />
			<Footer />
		</div>
	);
}