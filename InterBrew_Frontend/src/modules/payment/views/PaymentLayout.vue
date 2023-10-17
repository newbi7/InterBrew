<template>
  <v-app style="overflow-x: auto; overflow-y: hidden">
    <!-- header 부분 -->
    <v-app-bar app color="#FBE8C9" style="min-width: 100%; font-size: 14px">
      <v-icon></v-icon>
      <v-icon>mdi-coffee</v-icon>
      <v-toolbar-title> " {{ userName }} " 님 반갑습니다. </v-toolbar-title>
      <v-spacer></v-spacer>
      <div style="cursor: pointer; margin-right: 30px" @click="mypage">
        <v-icon>mdi-account</v-icon>
        마이페이지
      </div>
      <div style="cursor: pointer; margin-right: 30px" @click="logout">
        <v-icon>mdi-logout</v-icon>
        로그아웃
      </div>
    </v-app-bar>

    <!-- 상품목록  -->
    <v-main
      style="
        background-color: #fff6e6;
        min-width: 100%;
        min-height: 100%;
        margin-top: 20px;
      "
    >
      <v-row rows="2" style="margin-left: 100px">
        <v-col
          v-for="coffee in coffeeList"
          :key="coffee.coffeeId"
          cols="3"
          sm="6"
          md="4"
          lg="3"
          id="thumbimagecontrol"
        >
          <!-- 커피 항목 표시 -->
          <a @click="handleImageClick(coffee)">
            <v-img
              :src="coffee.coffeeImage"
              class="img-thumbnail"
              style="max-width: 100%; max-height: 100%"
            />
          </a>
          <div style="justify-center;">{{ coffee.coffeeName }}</div>
        </v-col>
      </v-row>

      <v-navigation-drawer
        permanent
        location="right"
        style="background-color: #white"
        class="v-navigation-drawer"
      >
        <br />
        <h1
          class="d-flex justify-center"
          style="color: #ffc66b; font-size: 30px"
        >
          Order List
        </h1>
        <hr />
        <div class="bottom-section">
          <div
            class="d-flex justify-center"
            style="background-color: white; margin-bottom: 5px; font-size: 20px"
          >
            총액 : &#x20A9; {{ totalAmount }} 
          </div>
          <div
            class="d-flex justify-between"
            style="flex-direction: row; background-color: white"
          >
            <v-btn class="orderbutton" @click="cancelOrder">취소하기</v-btn>
            <v-btn class="orderbutton" @click="placeOrder">주문하기</v-btn>
          </div>
        </div>
        <v-list>
          <v-list-item
            v-for="(item, index) in orderedItems"
            :key="index"
            style="background-color: white; margin-top: 5px"
          >
            <v-list-item>
              <div>{{ item.coffeeName }} x {{ item.quantity }}</div>
              <div>&#x20A9; {{ item.quantity * item.coffeePrice }} </div>
            </v-list-item>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
    </v-main>

    <!-- 모달창 -->
    <v-dialog v-model="dialogVisible" max-width="900px">
      <v-card>
        <v-card-title style="background-color: #bcaaa4; height: 60px">
          <span id="thumbimagecontrol" class="headline d-flex justify-center">
            {{ selectedCoffee.coffeeName }}
          </span>
        </v-card-title>
        <v-card-text class="d-flex">
          <v-img
            :src="selectedCoffee.coffeeImage"
            style="
              width: 180px;
              height: 180px;
              margin-right: 20px;
              object-fit: contain;
            "
          />
          <div class="flex-grow-1">
            <span>{{ selectedCoffee.coffeeInformation }}</span>
          </div>
        </v-card-text>
        <div class="d-flex justify-center">
          <v-btn class="numbercontrolbutton" @click="decreaseNumber">-</v-btn>
          <span
            style="
              font-size: 20px;
              margin-left: 20px;
              margin-right: 20px;
              text-align: center;
            "
            >{{ number }}</span
          >
          <v-btn class="numbercontrolbutton" @click="increaseNumber">+</v-btn>
        </div>

        <v-card-actions class="d-flex justify-center">
          <v-btn
            class="orderbutton"
            color="brown"
            text
            @click="dialogVisible = false"
            style="background-color: #efebe9"
            >닫 기</v-btn
          >

          <v-btn
            class="orderbutton"
            color="brown"
            text
            style="background-color: #efebe9"
            @click="addItemToOrder(selectedCoffee)"
            >주 문</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useOrderStore } from "@/stores/orderStore";
import { useUserStore } from "@/stores/userStore";
import { useCoffeeStore } from "@/stores/coffeeStore";
import { $redisSave } from "@/api/payment";

export default {
  setup() {
    const router = useRouter();
    const dialogVisible = ref(false);
    const selectedCoffee = ref("");
    const number = ref(1);
    const orderedItems = ref([]);
    const orderStore = useOrderStore();
    const userStore = useUserStore();
    const userId = userStore.userId;
    const userName = userStore.userName;
    const coffeeStore = useCoffeeStore();
    let coffeeList = ref([]);
    const totalAmount = computed(() => {
      let total = 0;
      for (const item of orderedItems.value) {
        total += item.quantity * item.coffeePrice;
      }
      return total;
    });

    function handleImageClick(coffee) {
      selectedCoffee.value = coffee;
      const coffeeCount = selectedCoffee.value.coffeeCount;
      if (coffeeCount == 0) {
        alert(selectedCoffee.value.coffeeName + " 의 커피수량이 부족합니다");
      } else {
        dialogVisible.value = true;
        number.value = 1;
      }
    }

    function decreaseNumber() {
      if (number.value > 1) {
        number.value--;
      }
    }

    function increaseNumber() {
      number.value++;
    }

    function addItemToOrder(selectedCoffee) {
      const coffeeNo = selectedCoffee.coffeeId;
      const coffeeName = selectedCoffee.coffeeName;
      const coffeePrice = selectedCoffee.coffeePrice;
      const coffeeCount = selectedCoffee.coffeeCount;

      if (coffeeCount - number.value < 0) {
        alert(coffeeName + " 의 커피 잔고가 부족합니다");
        return;
      }

      const existingItem = orderedItems.value.find(
        (item) => item.coffeeName === coffeeName
      );
      if (coffeeName && number.value > 0) {
        if (existingItem) {
          existingItem.quantity += number.value;
        } else {
          const newItem = {
            coffeeNo: coffeeNo,
            coffeeName: coffeeName,
            coffeePrice: coffeePrice,
            quantity: number.value,
          };
          orderedItems.value.push(newItem);
        }
        dialogVisible.value = false;
      }
    }

    function cancelOrder() {
      orderedItems.value = [];
    }

    function logout() {
      orderStore.clearAll();
      userStore.clearUser();
      router.push("/");
    }

    function mypage() {
      router.push("/mypage");
    }

    async function placeOrder() {
      if (totalAmount.value === 0) {
        alert("주문 하신 상품이 없습니다. 상품을 선택해주세요.");
      } else {
        for (const orderedItem of orderedItems.value) {
          const coffee = coffeeList.value.find(
            (coffee) => coffee.coffeeName === orderedItem.coffeeName
          );
          if (coffee && coffee.coffeeCount < orderedItem.quantity) {
            alert(`${coffee.coffeeName} 의 커피 잔고가 부족합니다.`);
            return; 
          }
        }
        orderStore.setOrderedItems(orderedItems.value);
        orderStore.setTotalAmount(totalAmount.value);
        const paymentTotalCount = orderedItems.value.reduce(
          (total, item) => total + item.quantity,
          0
        );

        try {
          const payment = {
            userNo: userStore.userNo,
            userId: userStore.userId,

            payment: orderedItems.value,
            paymentPrice: totalAmount.value,
            paymentTotalCount: paymentTotalCount,
          };
          const redisResponse = await $redisSave(payment);
          if (!redisResponse.data.message) {
            router.push("/paycheck");
          } else {
            console.error(redisResponse);
            alert(
              "주문오류가 발생했습니다" + "\n" + redisResponse.data.message
            );
          }
        } catch (error) {
          alert("주문오류");
          console.error("주문오류", error);
        }
      }
    }

    function loadCoffeeData() {
      coffeeList.value = coffeeStore.coffee;
    }

    onMounted(() => {
      loadCoffeeData();
    });

    return {
      dialogVisible,
      selectedCoffee,
      handleImageClick,
      number,
      decreaseNumber,
      increaseNumber,
      orderedItems,
      addItemToOrder,
      cancelOrder,
      placeOrder,
      totalAmount,
      logout,
      mypage,
      loadCoffeeData,
      coffeeList,
      userId,
      userName,
    };
  },
};
</script>

<style scoped>
* {
  font-family: "Malgun Gothic", sans-serif !important;
}

hr {
  border-top: 5px solid white;
  margin-top: 10px;
  margin-bottom: 10px;
}

h1 {
  color: #4e342e;
  font-family: "Malgun Gothic", sans-serif;
  font-weight: bold;
}

.v-navigation-drawer {
  position: fixed;
  top: 0;
  right: 0;
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  min-width: 400px;
  z-index: 999;
}

.img-thumbnail {
  width: 180px;
  height: 180px;
  margin: 10px;
  cursor: pointer;
}

.numbercontrolbutton {
  min-height: 11px;
  min-width: 11px;
  max-height: 25px;
  max-width: 25px;
  background: #fbe9e7;
}

.orderbutton {
  font-size: 13px;
  margin-left: 10px;
  margin-right: 10px;
  min-width: 160px;
  max-width: 185px;
}

.bottom-section {
  position: top;
  bottom: 0;
  width: 100%;
  background-color: white;
  font-size: 25px;
  padding: 10px;
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
}

#thumbimagecontrol {
  margin: 15px;
  font-size: 28px;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

@media (max-width: 768px) {
  .v-navigation-drawer {
    width: 100%;
    font-size: 20px;
  }

  .v-main {
    width: 100%;
    height: 100%;
  }
  .v-row {
    width: 100%;
    height: 100%;
  }
  .v-col {
    width: 100%;
    height: 100%;
  }

  .img-thumbnail {
    width: 100%;
    height: auto;
  }

  .v-toolbar-title {
    font-size: 20px;
  }

  .orderbutton {
    min-width: auto;
    width: 100%;
    font-size: 12px;
  }

  .bottom-section {
    font-size: 20px;
  }

  #thumbimagecontrol {
    font-size: 20px;
  }

  #Gothic {
    font-family: "Malgun Gothic", sans-serif;
  }
}
</style>
