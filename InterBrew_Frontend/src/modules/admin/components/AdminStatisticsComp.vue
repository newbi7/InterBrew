<template>
    <br>
    <br>
    <v-row>
        <v-col cols="12" sm="6">
            <v-card class="mx-auto" style="min-height: 400px;" variant="outlined">
            <v-card-item>
                <div>
                    <div class="text-overline mb-1">ITEM STATUS</div>
                    <div class="text-h6 mb-1">재고현황</div>
                    <v-list>
                        <v-list-item v-for="item in coffeeList" :key="item.coffeeNo">
                            <v-list-item :class="{ 'red-text': item.coffeeCount <= 5 }">
                                - {{ item.coffeeName }}  :  {{ item.coffeeCount }} 개
                                <span v-if="item.coffeeCount <= 5" class="red-text">(!재고부족!)</span>
                            </v-list-item>
                        </v-list-item>
                    </v-list>
                </div>
            </v-card-item>
        
            <v-card-actions>
                <v-btn variant="outlined" @click="goToItemManagement"> 재고관리로 이동 </v-btn>
            </v-card-actions>
            </v-card>
        </v-col>
        <v-col cols="12" sm="6">
            <v-card class="mx-auto" style="margin-bottom: 10px;" variant="outlined">
            <v-card-item>
                <div>
                    <div class="text-overline mb-1">PAYMENT STATUS</div>
                    <div class="text-h6 mb-1">구매현황</div>
                    <v-list>
                        <v-list-item>
                            <v-list-item> ● 이번달 구매 총 금액 : {{ payments.paymentMonthlyTotalPrice }} 원</v-list-item>
                        </v-list-item>
                        <v-list-item>
                            <v-list-item> ● 이번달 구매 총 수량 : {{ payments.paymentMonthlyTotalCount }} 개</v-list-item>
                        </v-list-item>    
                    </v-list>
                </div>
            </v-card-item>
            <v-card-actions>
                <v-btn variant="outlined" @click="goToPaymentManagement"> 구매관리로 이동 </v-btn>
            </v-card-actions>
            </v-card>
            <br>
            <v-card class="mx-auto" variant="outlined">
            <v-card-item>
                <div>
                    <div class="text-overline mb-1">REQUEST STATUS</div>
                    <div class="text-h6 mb-1">요구사항 처리 현황</div>
                    <v-list>
                        <v-list-item>
                            <v-list-item> ● 미처리 요구사항 수 : {{ unsolvedInquiry }} 건</v-list-item>
                        </v-list-item>
                    </v-list>
                </div>
            </v-card-item>
            <v-card-actions>
                <v-btn variant="outlined" @click="goToRequestManagement"> 요구사항관리로 이동 </v-btn>
            </v-card-actions>
            </v-card>
        </v-col>
    </v-row>
  </template>
  

  <script>
  import { ref, onMounted } from 'vue';
  import { useRouter } from 'vue-router';
  import { useCoffeeStore } from '@/stores/coffeeStore';
  import { $getSumByDate } from '@/api/payment' 
  import { $getTheNumOfUnsolvedInquiry } from '@/api/inquiry'

  export default {
    name: "AdminStatisticsComp",
    setup() {
        const router = useRouter();

        const coffeeStore = useCoffeeStore();
        const coffeeList = ref(coffeeStore.coffee);
        
        const payments = ref([]);

        const unsolvedInquiry = ref([]);

        const currentDate = new Date(); 
        const currentYear = currentDate.getFullYear(); 
        const currentMonth = String(currentDate.getMonth() + 1).padStart(2, '0'); 
        const dateForm = ref(`${currentYear}-${currentMonth}`);

        const goToItemManagement = () => {
            router.push('/admin/item');
        };

        const goToPaymentManagement = () => {
            router.push('/admin/payment/detail');
        };

        const goToRequestManagement = () => {
            router.push('/admin/inquiry');
        };

        
        // 날짜 형식 변환
        function formatDateToFullString(dateForm) {
            return `${dateForm}-01 00:00:00`;
        }
        
        // 커피 재고 현황 조회
        function loadCoffeeData(){
            coffeeList.value = coffeeStore.getCoffee;
            console.log(coffeeList.value);
        }

        // 구매 내역 월별 전체 통계 조회
        async function getSumByDate(date){
            const formattedMonth = formatDateToFullString(date);
            console.log(formattedMonth);
            try{
                const res = await $getSumByDate(formattedMonth);
                if (res.data != null || res.data != ''){
                    console.log(res.data);
                    payments.value = res.data;
                } 
            } catch (err) {
                console.log(err);
            }
        }

        // 미처리 요구사항 건수 조회
        async function getTheNumOfUnsolvedInquiry(){
            try{
                const res = await $getTheNumOfUnsolvedInquiry();
                if (res.data != null || res.data != ''){
                    console.log(res.data);
                    unsolvedInquiry.value = res.data;
                } 
            } catch (err) {
                console.log(err);
            }        
        }

        onMounted(() => {
            loadCoffeeData();
            getSumByDate(dateForm.value);
            getTheNumOfUnsolvedInquiry();
        });

      return {
        payments,
        unsolvedInquiry,
        coffeeList,
        goToItemManagement,
        goToPaymentManagement,
        goToRequestManagement
      };
    }
  }
  </script>
  


<style scoped>
.red-text {
    color: red;
    background-color: rgb(255, 252, 188);
}
</style>