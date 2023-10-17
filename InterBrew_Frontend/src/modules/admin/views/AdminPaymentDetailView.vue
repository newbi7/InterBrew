<template>
    <div>
      <admin-payment-detail-comp></admin-payment-detail-comp>
    </div>
  </template>
  
  <script>
  import AdminPaymentDetailComp from '@/modules/admin/components/AdminPaymentDetailComp.vue';
 
  export default {
    name: 'AdminPaymentDetailView',
    components: {
      AdminPaymentDetailComp
    },
  };
  </script>
  
  <style>
  </style>