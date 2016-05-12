with (new JavaImporter(org.roy.loadx.sample)) {
  setDefaultScenarioIterationCount(2)
  setDefaultScenarioRunIterationCount(2)
  setDefaultScenarioUserCount(2)

  getScenarioClassData(SampleScenario).put(SampleScenario.Data.URL, "http://somewhere.com")

  scenario = addScenario(SampleScenario)
  scenario.getObjectData().put(SampleScenario.Data.SCENARIO_TYPE, 
    SampleScenario.ScenarioType.GREAT)

  scenario = addScenario(SampleScenario)
  scenario.getObjectData().put(SampleScenario.Data.SCENARIO_TYPE, 
    SampleScenario.ScenarioType.BETTER)

  setJobInitializer(new SampleJobInitializer())
}