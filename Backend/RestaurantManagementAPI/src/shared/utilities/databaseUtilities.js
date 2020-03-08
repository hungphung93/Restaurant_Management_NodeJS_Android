import mongoose from 'mongoose';

export const getEntity = (entityName, schema) => {
    return mongoose.model(entityName, schema);
}


