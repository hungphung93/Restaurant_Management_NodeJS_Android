export default class AuthenticatedUser {
    constructor(username, accessToken, role) {
        this.username = username;
        this.accessToken = accessToken;
        this.role = role;
    }
}