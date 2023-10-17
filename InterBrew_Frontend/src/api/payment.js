import { createAxiosInstance } from "./index";

// 관리자 - 월별 전체 통계 조회
async function $getSumByDate(month){
    try {
        const axios = createAxiosInstance();
        return await axios.get(`/payment/admin/${month}/statistics`);
    } catch (err) {
        console.log(err);
    }
}

// 관리자 - 전체 회원 결제 내역 조회
async function $getPaymentListByDate(month){
    try {
        const axios = createAxiosInstance();
        return await axios.get(`/payment/admin/${month}/detail`);
    } catch (err) {
        console.log(err);
    }
}

// 관리자 - 월별 회원별 결제 총 금액, 수량 조회
async function $getSumByDateAndUser(month){
    try {
        const axios = createAxiosInstance();
        return await axios.get(`/payment/admin/${month}/total`);
    } catch (err) {
        console.log(err);
    }
}

async function $redisSave(payment){
    try {
        const axios = createAxiosInstance();
        return await axios.put(`/payment/redis`, payment);
    } catch (err) {
        console.log(err);
    }
}

async function $orderSave(){
    try {
        const axios = createAxiosInstance();
        return await axios.put(`/payment`);
    } catch (err) {
        console.log(err);
    }
}

// 마이페이지 - 월별 결제 내역 조회
async function $getMyPaymentListByDate(month, userNo){
    try {
        const axios = createAxiosInstance();
        return await axios.get(`/payment/mypage/${month}/${userNo}/detail`);
    } catch (err) {
        console.log(err);
    }
}

export { $getPaymentListByDate, $getSumByDateAndUser, $redisSave, $orderSave, $getSumByDate, $getMyPaymentListByDate }