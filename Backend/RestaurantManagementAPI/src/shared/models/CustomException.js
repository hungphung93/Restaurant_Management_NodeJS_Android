export default class CustomException extends Error {

    constructor(isBusinessException, message) {
        super(message);
        this.isBusinessException = isBusinessException;
    }
}