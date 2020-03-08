import bcrypt from 'bcrypt'

export const encrypt = (value) => {
    return bcrypt.hashSync(value, 10);
}

export const compare = (value, hashedValue) => {
    return bcrypt.compareSync(value, hashedValue);
}
