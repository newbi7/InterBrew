<template>
    <div class = "inquiry-page">
      <mypage-inquiry-comp></mypage-inquiry-comp>
    </div>
</template>
  
<script>
  import MypageInquiryComp from '../components/MypageInquiryComp.vue';
  
  export default {
    name: 'MypageInquiryView',
    components: {
      MypageInquiryComp
    },
  };
</script>
  
<style>
</style>