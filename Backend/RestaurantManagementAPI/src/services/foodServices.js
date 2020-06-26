import * as foodRepositories from '../repositories/foodRepositories';
import { Food } from '../models/Food';

export const addNewFood = async (food) => {
    try {
        let createdFood = await foodRepositories.addNewFood(food);

        return await new Food(createdFood._id, createdFood.name,
            createdFood.description, createdFood.image_url,
            createdFood.category, createdFood.price,
            createdFood.is_deleted, createdFood.deleted_at);

    } catch (err) {
        throw err;
    }
}

export const getAllFoods = async () => {
    try {
        var lstFoodEntities = await foodRepositories.getAllFoods();

        var lstFoods = lstFoodEntities.map((x) => {
            return new Food(x._id, x.name, x.description,
                x.image_url, x.category, x.price,
                x.is_deleted, x.deleted_at);
        });

        return await lstFoods;
    } catch (err) {
        throw err;
    }
}