<template>
  <v-container>
    <v-row>
      <template  v-if="test">
        <v-col cols="12" class="text-h4">{{ test.title }}</v-col>
        <v-col cols="12" class="text-h5">{{ test.text }}</v-col>
        <v-col cols="12" class="text-h6">Creation date: {{ test.date == null ? "Not creation date" : test.date }}
        </v-col>
        <v-col cols="12" class="text-h5">Options:
          <v-row>
            <v-col cols="6" sm="4" lg="2" xl="3" md="3" :key=key v-for="(checkbox,key) in test.options">
              <v-checkbox :disabled="complete" v-model="optionsSelect[key]"
                          :label="`${checkbox.toString()}`">
              </v-checkbox>
            </v-col>
          </v-row>
          <small v-if="!complete" class="subtitle-2">
            <v-icon>mdi-arrow-up-left</v-icon>
            Might not be the right choice
            <v-icon>mdi-arrow-up-right</v-icon>
          </small>
        </v-col>
        <v-col cols="12">
          <v-btn v-if="complete" @click="completeTest()">COMPLETE</v-btn>
          <v-btn v-else @click="solveTest()">CHECK</v-btn>
        </v-col>
      </template>
      <v-col v-if="alert" cols="12">
        <v-alert :type="alert.success ? 'success': 'error'">{{ alert.feedback }}<br><small>{{ alert.date }}</small>
        </v-alert>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import webquizservice from "../service/webquizservice";

export default {
  name: 'GetView',
  data() {
    return {
      id: this.$route.params.id,
      complete: true,
      test: null,
      optionsSelect: [],
      alert: null,
      typeOfAlert: 'info',
    }

  },
  created() {
    webquizservice.getTest(this.id).then((response) => {
      console.log(response.data);
      this.test = response.data;
      this.optionsSelect = [this.test.length];
      this.alert = null
      this.typeOfAlert = 'info'
    }).catch((error) => {
      if (error.code = 404) {
        this.typeOfAlert = 'error';
        this.alert=Object
        this.alert.feedback = 'Bad request, check id of quiz'
      }
    });
  },
  methods: {

    completeTest() {
      this.complete = false;
    },
    solveTest() {
      let selected = [];
      var idx = this.optionsSelect.indexOf(true);
      while (idx != -1) {
        selected.push(idx);
        idx = this.optionsSelect.indexOf(true, idx + 1);
      }
      console.log(selected)
      webquizservice.completeTest(this.id, selected).then((response) => {
        this.alert = response.data
        this.alert.date = new Date();
      });
    },
  }
}
</script>
