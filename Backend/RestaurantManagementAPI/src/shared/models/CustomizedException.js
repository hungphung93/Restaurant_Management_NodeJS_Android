export default class CustomizedException extends Error {
    constructor(errorCode, message) {
        super();
        this.code = errorCode;
        this.message = message;
    }
}