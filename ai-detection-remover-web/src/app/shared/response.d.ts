interface CustomResponse<T = any> {
	code: string;
	body: T;
	message: string;
}
