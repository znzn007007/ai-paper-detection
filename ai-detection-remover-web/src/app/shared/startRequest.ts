import axios, { AxiosRequestConfig } from "axios";

const maxRetryTimes = 2;

axios.interceptors.response.use(
	(response) => {
		return response;
	},
	(error) => {
		const {response, config} = error;
		if (response) {
			const {status} = response;
			const {retryTimes = 0} = config;

			switch (status) {
				case 500:
					if (retryTimes <= maxRetryTimes) {
						return axios.request({...config, retryTimes: retryTimes + 1});
					}
					break;
				default:
			}
			throw error;
		} else {
			throw error;
		}
	},
);

export default async <T>(config: AxiosRequestConfig): Promise<CustomResponse<T>> => {
	return axios
		.request(config)
		.then((response) => {
			const {status, data} = response;

			if (status === 200) {
				return data;
			} else {
				throw response;
			}
		})
		.catch((err) => {
			const {message, config = {}, code} = err || {};
			const {baseURL, url} = config;
			console.log({message, code, url: baseURL + url});
			throw err;
		});
};
