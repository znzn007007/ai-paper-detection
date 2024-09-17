import startRequest from "@/app/shared/startRequest";
import requestConfig from "@/app/shared/requestConfig";
import urls from "@/app/shared/urls";

class Request {
	get<T = any>(path?: string, params?: any, config?: any) {
		return this.request<T>("GET", urls.base_url, path, params, config);
	}

	post<T = any>(path?: string, params?: any, config?: any) {
		return this.request<T>("POST", urls.base_url, path, params, config);
	}

	request<T = any>(method: "GET" | "POST", baseUrl: string, path?: string, params?: any, config?: any) {
		console.log(method, path);
		return startRequest<T>(requestConfig(method, baseUrl, path, params, config));
	}
}

const request = new Request();
export default request;