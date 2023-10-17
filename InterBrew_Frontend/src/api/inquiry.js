import { createAxiosInstance } from "./index";

// 관리자 - 미처리 요구사항 건수 조회
async function $getTheNumOfUnsolvedInquiry(){
    try {
        const axios = createAxiosInstance();
        return await axios.get( `/inquiry/admin/statistics`);
    } catch (err) {
        console.log('error msg : ', err)
    }
}

// 관리자 - 전체 요청사항 조회
async function $getAllInquiries(){
    try {
        const axios = createAxiosInstance();
        return await axios.get( `/inquiry/admin`);
    } catch (err) {
        console.log('error msg : ', err)
    }
}

// 관리자 - 요청사항 상태별 조회
async function $getInquiryByStatus(status){
    try {
        const axios = createAxiosInstance();
        return await axios.get(`/inquiry/admin/${status}`);
    } catch (err) {
        console.log(err);
    }
}

// 관리자 - 요청사항 상태 수정
async function $updateInquiryStatus(inquiryNo){
    try {
        const axios = createAxiosInstance();
        return await axios.post(`/inquiry/admin`, { inquiryNo: inquiryNo });
    } catch (err) {
        console.log(err);
    }
}

// 마이페이지 - 요청사항 생성
async function $createInquiry(inquiryContents, userNo, userId){
    try {
        const axios = createAxiosInstance();
        const inquiry = {
            inquiryContents: inquiryContents,
            userNo: userNo,
            userId: userId
        }
        return await axios.put(`/inquiry/mypage`, inquiry);
    } catch (err) {
        console.log(err);
    }
}

export { $getAllInquiries, $getInquiryByStatus, $updateInquiryStatus, $getTheNumOfUnsolvedInquiry, $createInquiry }