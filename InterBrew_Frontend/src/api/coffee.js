import { createAxiosInstance, createImageInstance } from "./index";

// 커피조회
async function $coffeeList() {
  try {
    const axios = createAxiosInstance();
    return await axios.get(`/coffee`);
  } catch (error) {
    console.error("coffeelist error" + error);
    throw error;
  }
}

async function $coffeeAdd(newCoffeeItem, coffeeImage, userId) {
  try {
    const axios = createImageInstance();
    const newCoffeeItems = new FormData();
    newCoffeeItems.append("newCoffeeItem", JSON.stringify(newCoffeeItem));
    newCoffeeItems.append("newCoffeeImage", coffeeImage.values().next().value); 
    newCoffeeItems.append("userId", userId);
    return await axios.put(`/coffee`, newCoffeeItems);
  } catch (error) {
    console.error("coffeelist error" + error);
    throw error;
  }
}

async function $coffeeModify(modifyCoffeeItem) {
  try {
    const axios = createAxiosInstance();
    const data = JSON.stringify(modifyCoffeeItem.value);
    return await axios.post(`/coffee`, data);
  } catch (error) {
      console.error("coffeelist error" + error);
    throw error;
  }
}

export { $coffeeList, $coffeeAdd, $coffeeModify};
