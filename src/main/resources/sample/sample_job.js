with (new JavaImporter(org.roy.loadx.sample)) {
  getJobData().put(SampleJobInitializer.Data.PROJECT, "sample job")
  setJobInitializer(new SampleJobInitializer())

  setDefaultScenarioIterationCount(2)
  setDefaultScenarioRunIterationCount(2)
  setDefaultScenarioThreadCount(2)

  getScenarioClassData(SampleScenario).put(SampleScenario.Data.URL, "http://somewhere.com")

  scenario = addScenario(SampleScenario)
  scenario.getScenarioData().put(SampleScenario.Data.SCENARIO_TYPE,
    SampleScenario.ScenarioType.GREAT)

  scenario = addScenario(SampleScenario)
  scenario.getScenarioData().put(SampleScenario.Data.SCENARIO_TYPE, 
    SampleScenario.ScenarioType.BETTER)
}