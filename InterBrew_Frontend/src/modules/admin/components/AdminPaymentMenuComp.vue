<template>
    <br>
    <v-tabs bg-color="white">
        <v-tab>
            <router-link to="/admin/payment/total" class="text"> 회원별 월결제 금액</router-link>
        </v-tab>
        <v-tab>
            <router-link to="/admin/payment/detail" class="text"> 월별 전체 세부내역 </router-link>
        </v-tab>
    </v-tabs>
</template>

<script>

export default {
    name : "AdminPaymentMenuComp",
}

</script>

<style scoped>
 .text {
    font-family: 'IBMPlexSansKR-Regular';
    font-weight: 500;
    font-style: normal;
    font-size: 15px;
    line-height: 1.4;
    color: #5e4e4a;
    text-decoration: none;
  }
</style>