<template>
    <div>
      <admin-item-comp></admin-item-comp>
    </div>
  </template>
  
  <script>
  import AdminItemComp from '@/modules/admin/components/AdminItemComp.vue';
  
  export default {
    name: 'AdminItemView',
    components: {
      AdminItemComp
    },
  };
  </script>
  
  <style scoped>
  </style>