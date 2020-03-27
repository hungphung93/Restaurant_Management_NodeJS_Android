import { FoodEntity } from '../entities/FoodEntity';

export const addNewFood = async (food) => {
    try {
        console.log(food.foodDescription);
        let newFood = new FoodEntity();
        newFood.name = food.foodName;
        newFood.description = food.foodDescription;
        newFood.image_url = food.imageURL;
        newFood.category = food.category;
        newFood.price = food.price;


        const createdFood = await newFood.save();

        return await createdFood;
    } catch (err) {
        throw err;
    }
}

export const getAllFoods = async () => {
    try {
        let lstFood = await FoodEntity.find({ is_deleted: false });

        return await lstFood;
    } catch (err) {
        throw err;
    }
}