import * as userRepositories from '../repositories/userRepositories';
import * as encryptUtilities from '../shared/utilities/encryptUtilities';
import * as jwtUtilities from '../shared/utilities/jsonWebTokenUtilities';
import CustomException from '../shared/models/CustomException';
import * as errorMessage from '../shared/constants/errorMessages';
import AuthenticatedUser from '../models/AuthenticatedUser';

export const register = async (user) => {
    try {
        user.password = encryptUtilities.encrypt(user.password);

        return await userRepositories.register(user);
    } catch (err) {
        throw err;
    }

}

export const login = async (username, password) => {
    try {
        let createdUser = await userRepositories.login(username, password);

        if (!createdUser) throw new CustomException(true, errorMessage.INVALID_USERNAME_PASSWORD);

        if (!createdUser.comparePassword(password, createdUser.password)) throw new CustomException(true, errorMessage.INVALID_USERNAME_PASSWORD);

        let access_token = await jwtUtilities.createToken(username, createdUser.id, createdUser.role);

        return await new AuthenticatedUser(username, access_token, createdUser.role);
    } catch (err) {
        console.log(JSON.stringify(err));
        console.log("===============================");
        throw err;
    }
}
