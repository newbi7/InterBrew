<template>
  <div class = "mypageheader-container">
    <v-app-bar fixed color="#FFF5E5" app>
      <v-container fluid>
        <v-row no-gutters class="d-flex align-center">
          <v-col cols="auto" class="menu-item">
            <router-link to="/mypage" class="text">결제내역</router-link>
          </v-col>
          <v-col cols="auto" class="menu-item">
            <router-link to="/mypage/inquiry" class="text">요청하기</router-link>
          </v-col>
          <v-col cols="auto" class="menu-item">
            <router-link to="/mypage/changepw" class="text">비밀번호 변경</router-link>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="auto" class="menu-item">
    <router-link to="/payment" class="text">
      💳 결제페이지 <!-- 이모티콘 추가 -->
    </router-link>
          </v-col>
          <v-col cols="auto" class="menu-item">
            <router-link 
              to="/" 
              class="text logout" 
              @click="logout" 
              style="align-items: center;"
            >
              🚪 로그아웃 <!-- 이모티콘 추가 -->
            </router-link>
          </v-col>
        </v-row>
      </v-container>
    </v-app-bar>
  </div>
</template>
  
  
<script>
import { useRouter } from "vue-router";
import { useOrderStore } from "@/stores/orderStore";
import { useUserStore } from "@/stores/userStore";

export default {
  setup() {
    const router = useRouter();
    const userStore = useUserStore();
      const orderStore = useOrderStore();

    function logout() {
      orderStore.clearAll();
      userStore.clearUser();
      router.push("/");
    }

    return {
      logout
    }
  }
}
</script>

<style scoped>

  .mypageheader-container {
    
  }
  .v-app-bar {
    height: 70px;
  }
  
  .logo-container {
    padding-left: 2vw;
  }
  
  .logo-image {
    height: 75px;
    margin-left: 34px;
    margin-top: 20px;
  }
  
  .text {
  font-family: 'IBMPlexSansKR-Regular';
  font-weight: 800; /* 굵게 설정 */
  font-style: normal;
  font-size: 24px;
  line-height: 100px;  /* 상하 중앙 정렬을 위해 line-height를 80px로 설정 */
  vertical-align: middle;  /* 상하 중앙 정렬 */
  color: #5e4e4a;
  text-decoration: none;
  }
  
  .menu-item {
    margin-left: 2vw;
  }
  
  .menu-item:last-child {
    margin-right: 0;
  }
  
  .logout {
    text-decoration: blink;
    color: rgb(64, 75, 36);
    font-weight: 800;
  }
  </style>