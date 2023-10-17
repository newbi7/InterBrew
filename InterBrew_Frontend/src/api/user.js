import { createAxiosInstance } from "./index";

// 로그인
async function $login(userId, userPw) {
    try {
        const axios = createAxiosInstance();
        const data = {
            userId: userId,
            userPw: userPw,
          };
        return await axios.post( `/user/login`, data);
    } catch (error) {
        console.error("loginError" + error);
        throw error;
    }
}

// 회원가입
async function $userCreate(userId, userPw, userName) {
    try {
        const axios = createAxiosInstance();
        const data = {
            userId: userId,
            userPw: userPw,
            userName: userName,
          };
        return await axios.put( `/user/auth`, data);
    } catch (error) {
        console.error("userCreateError" + error);
        throw error;
    }
}

// 비밀번호 변경
async function $changePw(userId ,userPw, newPw) {
    try {
        const axios = createAxiosInstance();
        const data = {
            userId: userId,
            userPw: userPw,
            newPw: newPw
        }
        return await axios.post( `/mypage/pw`, data);
    } catch (error) {
        console.error("chagePwError" + error);
        throw error;
    }
}

export { $login, $userCreate, $changePw }