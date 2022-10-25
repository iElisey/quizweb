<template>
  <v-container>
    <div v-if="currentUser !== null">
      <h1>Create</h1>
      <v-form
          ref="form"
          v-model="valid"
          lazy-validation
      >
        <v-row>
          <v-col cols="6">
            <v-text-field :rules="[rules.required]"
                          v-model="title" label="Title"></v-text-field>
          </v-col>
          <v-col cols="6">
            <v-text-field v-model="text" label="Text"></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-combobox :rules="[rules.required, rules.length]" label="Options" clearable multiple chips
                        v-model="options">
              <template v-slot:selection="{ attrs, item, select, selected }">
                <v-chip
                    v-bind="attrs"
                    :input-value="selected"
                    close
                    close-icon="mdi-check-bold"
                    @click="select"
                    :color="answers.includes(item) ? 'success' : ''"
                    @click:close="setAnswer(item)">
                  <strong>{{ item }}</strong>&nbsp;
                </v-chip>
              </template>
            </v-combobox>
          </v-col>

        </v-row>
        <v-btn :disabled="!valid"
               @click="create">Create
        </v-btn>
        <v-alert class="mt-4" v-if="alert" :type="alert.success?'success':'error'">
          <div v-if="alert.error">{{ this.alert.error }}</div>
          <div v-else>
            SUCCESS!
            <v-btn color="white" class="black--text" :to="'/get/'+this.alert.id">GET</v-btn>
          </div>
        </v-alert>
      </v-form>
    </div>
    <div v-else>
      <h1><v-icon>mdi-warning</v-icon>Будь ласка, авторизуйтесь, щоб створювати вікторини :)</h1>
    </div>
  </v-container>
</template>

<script>
import Webquizservice from "../service/webquizservice";

export default {
  name: "CreateView",
  data() {
    return {
      valid: false,
      text: "",
      title: "",
      options: [],
      answers: [],
      alert: null,
      rules: {
        length: values => values.length >= 2 || 'Length must be >=2',
        required: value => !!value || 'Required.',
        counter: value => value.length <= 20 || 'Max 20 characters',
        email: value => {
          const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
          return pattern.test(value) || 'Invalid e-mail.'
        },
      },
    }
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    setAnswer(item) {
      if (this.answers.includes(item)) {
        this.answers.splice(this.answers.indexOf(item), 1);
      } else {
        this.answers.push(item);
      }
    }
    ,
    create() {
      let validate = this.$refs.form.validate();
      if (validate) {
        let answers = [];
        this.answers.forEach(el => {
          answers.push(this.options.indexOf(el));
        })
        Webquizservice.createTest(this.title, this.text, this.options, answers).then((response) => {
          this.alert = response.data;
          this.alert.success = true;
          console.log(response.data);
        }).catch((error) => {
          this.alert = error.response.data;
          console.log(error.response.data)
          this.alert.success = false;
        });
      }
    }
    ,

  }

}
</script>

<style scoped>

</style>