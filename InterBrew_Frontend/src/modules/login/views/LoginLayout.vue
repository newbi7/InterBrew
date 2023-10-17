<template>
  <v-app class="page-background">
    <v-row align="center" justify="end" class="row-bottom-margin">
      <v-col cols="16" sm="8" md="4">
        <!-- 로그인 창 -->
        <v-card color="brown" rounded>
          <v-card-title class="text-center">
            <br />
            <h2>
              Ploonet Coffe-seller
            </h2>
            <br />
          </v-card-title>
          <v-card-text>
            <v-form @submit.prevent="login">
              <v-text-field
                v-model="username"
                label="E-mail 또는 성함을 입력해주세요"
                required
                outlined
              ></v-text-field>
              <v-text-field
                v-model="password"
                label="Password default : 0000"
                type="password"
                required
                outlined
              ></v-text-field>
              <v-btn type="submit" color="brown" block>
                <v-icon right> mdi-key </v-icon>
                &nbsp; 로 그 인
              </v-btn>
            </v-form>
            <br />
            <v-btn
              @click="showSignUpModal = true"
              type="submit"
              color="brown"
              block
            >
              <v-icon right> mdi-account </v-icon>
              &nbsp; 회 원 가 입
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- 회원가입 창 -->
    <v-dialog v-model="showSignUpModal" max-width="600px">
      <v-card style="color: rgb(148, 38, 38)">
        <v-card-text style="background-color: brown, 0.7">
          <v-text-field
            v-model="newUserId"
            label="사용자 이메일"
            required
            outlined
          ></v-text-field>
          <v-text-field
            v-model="newUserPw"
            label="비밀번호"
            type="password"
            required
            outlined
          ></v-text-field>
          <v-text-field
            v-model="newUserName"
            label="사용자 이름"
            required
            outlined
          ></v-text-field>
        </v-card-text>
        <div class="d-flex justify-between">
          <v-btn
            class="newUserbtn"
            @click="closeSignUpModal"
            background-color="brown"
            >취소</v-btn
          >
          <v-btn class="newUserbtn" @click="signUp" background-color="brown"
            >회원가입</v-btn
          >
        </div>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import { useCoffeeStore } from '@/stores/coffeeStore';
import { $login, $userCreate } from "@/api/user"
import { $coffeeList } from "@/api/coffee"

export default {
  setup() {
    const username = ref("");
    const password = ref("");
    const router = useRouter();
    const userStore = useUserStore();
    const coffeeStore = useCoffeeStore();
    const showSignUpModal = ref(false);
    const newUserId = ref("");
    const newUserPw = ref("");
    const newUserName = ref("");

    async function login() {
      if (username.value != "" && password.value != "") {
        try {
          const userId = username.value
          const userPw = password.value
          // 로그인
          const loginResponse = await $login(userId, userPw);
          const userNo = loginResponse.data.userNo;
          const loginId = loginResponse.data.loginId;
          const userName = loginResponse.data.userName;
          const userType = loginResponse.data.userType;
          const userToken = loginResponse.data.token;

          // 오류발생안할시 if문
          if (!loginResponse.data.cause) {
            userStore.setUserNo(userNo);
            userStore.setUserId(loginId);
            userStore.setUserName(userName);
            userStore.setUserToken(userToken);

            // 로그인시 커피품목 미리 조회
            try{
            const coffee = await $coffeeList();
            coffeeStore.setCoffee(coffee.data);
            } catch(error) {
              alert("커피조회를 실패했습니다")
            }

            if (userType == "Y") {
              router.push("/admin/main");
            } else {
              router.push("/payment");
            }
          } else {
            alert(loginResponse.data.cause.message);
          }
        } catch (error) {
          console.error(error);
          alert("로그인시 오류가 발생했습니다");
        }
      } else {
        alert("빈칸을 입력해주세요");
      }
    }

    async function signUp() {

      // 이메일 정규화, 정규식체크용
      const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
      if (!emailRegex.test(newUserId.value)) {
        alert("올바른 이메일 주소를 입력해주세요.");
        return;
      }
      if (!newUserPw.value || !newUserName.value) {
        alert("모든 필수 정보를 입력해주세요.");
        return;
      }

      // 회원가입, 진행시 데이터체크
      try {
        const userId = newUserId.value;
        const userPw = newUserPw.value;
        const userName = newUserName.value;
        const signUpResponse = await $userCreate(userId, userPw, userName);

        if (!signUpResponse.data.cause) {
          const token = signUpResponse.data.token;

          if (token) {
            alert("회원 가입에 성공하였습니다.");
            showSignUpModal.value = false;
          } else {
            alert(signUpResponse.data.cause.message);
          }
        } else {
          alert(signUpResponse.data.cause.message);
        }
      } catch (exception) {
        console.error(exception);
        alert("회원가입 중 오류가 발생했습니다." + exception);
      }
    }

    function closeSignUpModal() {
      showSignUpModal.value = false;
    }

    return {
      username,
      password,
      login,
      showSignUpModal,
      closeSignUpModal,
      newUserId,
      newUserPw,
      newUserName,
      signUp,
    };
  },
};
</script>

<style scoped>
* {
  font-family: 'Malgun Gothic', sans-serif !important;
}


.page-background {
  background-image: url("@/assets/images/login/background.gif");
  background-color: #5c4033;
  background-size: 80%;
  background-repeat: no-repeat;
  background-position: left;
  min-height: 100vh;
}

.newUserbtn {
  margin-left: 50px;
  margin-right: 50px;
  margin-bottom: 10px;
  min-width: 200px;
  color: brown;
}

.row-bottom-margin {
  max-width: 100%;
  margin-right: 40px;
}
</style>
