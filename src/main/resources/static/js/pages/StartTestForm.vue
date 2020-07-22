<template>
    <v-row align="center" class="mr-5 ml-5">
        <v-col>
            <div class="text-left">
                <p class="mt-4 text-center primary--text font-weight-bold font-italic">{{ test.name }}</p>
            </div>
            <v-row class="mt-10" justify="center">
                <v-col sm="8">
                    <div v-if="!this.isFinish">
                        <v-card class="d-flex flex-column">
                            <v-card-title class="mt-5 headline">
                                <v-row justify="center">
                                    <v-col sm="10">
                                        <p class="text-center font-weight-bold font-italic">Вопрос {{ currentQuestCount }} / {{ length }}</p>
                                        <p class="mt-5 text-center">{{ textQuestion }}</p>
                                    </v-col>
                                </v-row>
                            </v-card-title>

                            <v-card-text class="text--primary">
                                <component
                                        v-bind:is="component"
                                        v-bind:valueQuestion="valueQuestion"
                                ></component>
                                <v-radio-group class="mt-10 pt-10" v-model="radioGroup" :mandatory="false">
                                    <v-radio
                                            v-for="item in valueAnswer"
                                            class="mt-5"
                                            off-icon="radio_button_unchecked"
                                            on-icon="radio_button_checked"
                                            :label="item.value"
                                            :value="item.number"
                                            @change="selectAnswer(item.number)"
                                    ></v-radio>
                                </v-radio-group>
                            </v-card-text>

                            <v-spacer></v-spacer>

                            <v-card-actions class="mb-10 justify-center">
                                <div class="mt-10 text-center">
                                    <v-btn class="mr-5 red--text" @click="previousQuestion()" :disabled="disabledPreviousButton" text rounded
                                    >
                                        <v-icon left>arrow_back</v-icon>
                                        Предыдущий
                                    </v-btn>
                                    <v-btn class="green--text" @click="nextQuestion()" text rounded
                                    >{{ changeNextButton }}
                            <v-icon right>arrow_forward</v-icon>
                                    </v-btn>
                                </div>
                            </v-card-actions>
                        </v-card>
                    </div>
                </v-col>
            </v-row>
            <div v-if="this.isFinish">
                <v-card class="d-flex flex-column">
                    <v-card-title class="mt-10 justify-center headline">
                        <p class="text-center font-weight-bold font-italic">Результат</p>
                    </v-card-title>

                    <v-card-text class="text--primary">
                        <p class="ml-10 mr-10 mt-10 text-justify">{{ finishValue }}</p>
                    </v-card-text>

                    <v-spacer></v-spacer>

                    <v-card-actions class="mb-10 justify-center">
                        <v-btn color="red" @click="backTest()" dark text rounded>Вернутся к тестам</v-btn>
                    </v-card-actions>
                </v-card>
            </div>
        </v-col>
    </v-row>
</template>

<script>
    import { mapGetters, mapActions, mapState } from 'vuex'
    import store from '../store/store'
    import TextTestComponent from '../components/TextTestComponent.vue'
    import ImageTestComponent from '../components/ImageTestComponent.vue'
    import AudioTestComponent from '../components/AudioTestComponent.vue'

    export default {
        name: 'StartTestForm',
        data: () => ({
            ...mapGetters(['getWindowHeight']),
            component: null,
            valueQuestion: '',
            textQuestion: '',
            valueAnswer: [],
            countAllResults: [],
            currentQuestItem: {},
            currentAnswerItem: {},
            currentWeight: 0,
            count: 0,
            length: 0,
            currentQuestCount: 1,
            changeNextButton: 'Следующий',
            disabledPreviousButton: false,
            radioGroup: null,
            isFinish: false,
            finishValue: ''
        }),

        computed: {
            ...mapState(['test', 'images', 'audios'])
        },

        created() {
            JSON.parse(this.test.result).forEach(e => this.countAllResults.push({ result: e.count, value: 0 }))
            this.currentQuestItem = JSON.parse(this.test.resultQuestions)[this.count]
            this.currentAnswerItem = JSON.parse(this.test.resultAnswers)[this.count]
            this.length = JSON.parse(this.test.resultQuestions).length
            this.valueAnswer = this.currentAnswerItem.value
            this.setComponent()
            this.checkButton()
        },

        methods: {
            ...mapActions(['saveStatisticsAction']),
            nextQuestion() {
                this.radioGroup = null
                this.countResultTest()
                if (JSON.parse(this.test.resultQuestions).length - 1 === this.count) {
                    this.finishTest()
                    this.isFinish = true
                    return
                }
                this.count++
                this.currentQuestCount++
                this.currentQuestItem = JSON.parse(this.test.resultQuestions)[this.count]
                this.currentAnswerItem = JSON.parse(this.test.resultAnswers)[this.count]
                this.valueAnswer = this.currentAnswerItem.value
                this.setComponent()
                this.checkButton()
            },
            previousQuestion() {
                this.count--
                this.currentQuestCount--
                this.currentQuestItem = JSON.parse(this.test.resultQuestions)[this.count]
                this.currentAnswerItem = JSON.parse(this.test.resultAnswers)[this.count]
                this.valueAnswer = this.currentAnswerItem.value
                this.setComponent()
                this.checkButton()
            },
            setComponent() {
                switch (this.currentQuestItem.questComponent.toString()) {
                    case 'BuildingTextForm':
                        this.component = TextTestComponent
                        this.textQuestion = this.currentQuestItem.value
                        break
                    case 'BuildingImageForm':
                        this.component = ImageTestComponent
                        this.textQuestion = this.currentQuestItem.value.text
                        const currentImage = this.images.find(item => item.guid === this.currentQuestItem.value.imageUuid)
                        this.valueQuestion = currentImage.image
                        break
                    case 'BuildingAudioForm':
                        this.component = AudioTestComponent
                        this.textQuestion = this.currentQuestItem.value.text
                        const currentAudio = this.audios.find(item => item.guid === this.currentQuestItem.value.audioUuid)
                        this.valueQuestion = currentAudio.audio
                        break
                }
            },
            checkButton() {
                this.disabledPreviousButton = this.count === 0
                if (JSON.parse(this.test.resultQuestions).length - 1 === this.count) {
                    this.changeNextButton = 'Результат'
                } else {
                    this.changeNextButton = 'Следующий'
                }
            },
            countResultTest() {
                const currentResult = this.countAllResults.find(e => e.result === this.currentWeight)
                currentResult.value = currentResult.value + 1
            },
            selectAnswer(weight) {
                this.currentWeight = parseInt(weight, 10)
            },
            finishTest() {
                let maxValue = Math.max.apply(Math, this.countAllResults.map(o => o.value ))
                const objResult = this.countAllResults.find(o => o.value === maxValue)
                const result = JSON.parse(this.test.result);
                result.forEach(item => {
                    if (item.count === objResult.result) {
                        this.finishValue = item.value
                        const statistics = {
                            id: '',
                            name: item.caption,
                            test: this.test,
                            user: null,
                            createDate: new Date().toISOString(),
                            result: this.finishValue
                        }
                        this.saveStatisticsAction(statistics)
                    }
                })
            },
            backTest() {
                this.$router.push('/profile/test')
            }
        },

        beforeRouteEnter(to, from, next) {
            const path = to.path.toString().split("/")
            const id = path[path.length - 1]
            store.dispatch('findTestByIdAction', id)
            next()
        }
    }
</script>

<style>

</style>