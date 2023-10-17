<template>
  <v-container style="margin-top: 50px; font-family: 'Inter', sans-serif">
    <v-card class="cardBackground">
      <v-card-title>
        <h1 style="margin: 10px">" {{ userName }} " 님</h1>
        <h3 style="margin: 10px">주문 정보</h3>
      </v-card-title>
      <v-card-text>
        <v-list>
          <v-list-item
            v-for="(item, index) in orderedItems"
            :key="index"
            style="
              border-bottom: 5px solid rgba(232, 211, 211, 0.568);
              padding: 10px;
            "
          >
            <v-list-item>
              <v-list-item-title>
                품목 : {{ item.coffeeName }}</v-list-item-title
              >
              수량 : {{ item.quantity }}, 가격 :
              &#x20A9; {{ item.quantity * item.coffeePrice }}
            </v-list-item>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>

    <v-card class="cardBackground">
      <h1 style="margin: 30px">총 결제 금액 : &#x20A9; {{ totalAmount }} </h1>
      <v-btn class="paycheckButton" @click="handleCancel"> 취소 </v-btn>
      <v-btn class="paycheckButton" @click="handleOrder"> 결제 </v-btn>
    </v-card>
  </v-container>
</template>

<script>
import { defineComponent } from "vue";
import { useOrderStore } from "@/stores/orderStore";
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "vue-router";
import { $orderSave } from "@/api/payment";

export default defineComponent({
  setup() {
    const orderStore = useOrderStore();
    const userStore = useUserStore();
    const router = useRouter();
    const userName = userStore.userName;

    const handleCancel = () => {
      orderStore.clearOrder();
      router.push("/payment");
    };

    async function handleOrder() {
      const orderResponse = await $orderSave();
      if (!orderResponse.data.message) {
        alert("결제완료");
        orderStore.clearAll();
        router.push("/");
      } else {
        alert(orderResponse.data.message);
      }
    }

    return {
      orderedItems: orderStore.orderedItems,
      totalAmount: orderStore.totalAmount,
      handleCancel,
      handleOrder,
      userName,
    };
  },
});
</script>

<style scoped>
.paycheckButton {
  margin-left: 50px;
  margin-right: 50px;
  margin-top: 10px;
  margin-bottom: 30px;
  min-width: 200px;
  min-height: 50px;
  font-size: 20px;
  color: black;
}

.cardBackground {
  background-color: #fdf8ef;
}
</style>
