<template>
    <v-app-bar fixed color="#FFF5E5" app>
      <v-container fluid>
        <v-row no-gutters class="d-flex align-center">
          <v-col cols="auto" class="menu-item">
            <router-link to="/admin/main" class="text">메인통계</router-link>
          </v-col>
          <v-col cols="auto" class="menu-item">
            <router-link to="/admin/item" class="text">재고관리</router-link>
          </v-col>
          <v-col cols="auto" class="menu-item">
            <router-link to="/admin/payment/total" class="text">구매내역</router-link>
          </v-col>
          <v-col cols="auto" class="menu-item">
            <router-link to="/admin/inquiry" class="text">요청내역</router-link>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="auto" class="menu-item">
            <router-link 
            to="/" 
            class="text sdsdsd" 
            @click="logout" 
            style="align-items: center;"
            >
            로그아웃</router-link>
          </v-col>
        </v-row>
      </v-container>
    </v-app-bar>
    <v-dialog v-model="dialog" persistent max-width="460px">
      <v-card style="border-radius: 16px; background-color: #FFF5E1;">
        <v-card-text>
          {{ messageBody }}
        </v-card-text>
        <v-card-actions class="justify-center">
          <v-btn color="#555555" @click="closeDialog">확인</v-btn>
          <v-btn color="#555555" @click="gotoInquiry">요청사항으로 가기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  
<script>
import { useRouter } from 'vue-router';
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { Client } from '@stomp/stompjs'
import { useOrderStore } from "@/stores/orderStore";
import { useUserStore } from "@/stores/userStore";
import SockJS from 'sockjs-client'
  
  export default {
    name: "AdminHeaderComp",
    setup() {
      const router = useRouter();
      const stompClient = ref(null)
      const userStore = useUserStore();
      const orderStore = useOrderStore();
      const messageBody = ref("");
      const dialog = ref(false); 

  
      function logout() {
        orderStore.clearAll();
      userStore.clearUser();
        router.push("/");
      }

      const connect = () => {
        const socket = new SockJS('http://localhost:80/ws')
        stompClient.value = new Client({
          webSocketFactory: () => socket,
          debug: (str) => {
            console.log(str)
          },
          onConnect: () => {
            stompClient.value.subscribe('/topic/alarm', (message) => {
              messageBody.value = message.body;
              dialog.value = true;
            });
          },
          onStompError: (error) => {
            console.log("Stomp error:", error)
          }
        })

        stompClient.value.activate()
      }

      function closeDialog() {
        dialog.value = false;
      }

      function gotoInquiry() {
        if (router.currentRoute.value.path === "/admin/inquiry") {
          // 현재 URL과 동일할경우 페이지를 새로고침
          window.location.reload();
        } else {
          dialog.value = false;
          router.push("/admin/inquiry");
        }
      }


      const disconnect = () => {
        if (stompClient.value) {
          stompClient.value.deactivate()
        }
      }

      onMounted(() => {
        connect()
      })

      onBeforeUnmount(() => {
        disconnect()
      })

      return {
        logout,
        connect,
        disconnect,
        messageBody,
        dialog,
        closeDialog,
        gotoInquiry
      }
    }
  };
  </script>
  
  <style scoped>

  .text {
    font-family: 'IBMPlexSansKR-Regular';
    font-weight: 700;
    font-style: normal;
    font-size: 20px;
    line-height: 175px;
    color: #5e4e4a;
    text-decoration: none;
  }
  
  .menu-item {
    margin-left: 2vw;
  }
  
  .menu-item:last-child {
    margin-right: 0;
  }
/*   
  @media (max-width: 768px) {
    .text {
      font-size: 18px;
    }
  } */
  
  .sdsdsd {
    text-decoration: blink;
      color: rgb(64, 75, 36);
  }
  </style>