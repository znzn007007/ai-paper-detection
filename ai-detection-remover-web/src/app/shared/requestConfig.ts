export default (
	method: "GET" | "POST",
	baseURL: string = process.env.BASE_URL as string,
	url?: string,
	params?: any,
	config: any = {},
) => {
	const headers = {
		...(config.header || {}),
	};
	return {
		method,
		baseURL,
		url,
		[method === "GET" ? "params" : "data"]: params,
		...config,
		headers,
	};
};
