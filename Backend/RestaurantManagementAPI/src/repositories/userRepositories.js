import { UserEntity } from '../entities/UserEntity';


export const login = async (username, password) => {
    try {

        let logginUser = await UserEntity.findOne({
            username: username
        });

        return await logginUser;
    } catch (err) {
        throw err;
    }
}

export const register = async (user) => {
    try {
        let newUser = new UserEntity();
        newUser.username = user.username;
        newUser.password = user.password;
        newUser.role = user.role;

        const createdUser = await newUser.save();

        createdUser.password = undefined;

        return await createdUser;
    } catch (err) {
        throw err;
    }

}
