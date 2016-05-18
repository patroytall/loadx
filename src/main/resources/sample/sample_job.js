with (new JavaImporter(org.roy.loadx.sample)) {
  setJobInitializer(new SampleJobInitializer())
  setScenarioClassInitializer(SampleScenario, new SampleScenarioClassInitializer())

  getJobData().put(SampleJobInitializer.Data.JOB, "job")

  setDefaultScenarioIterationCount(2)
  setDefaultScenarioRunIterationCount(2)
  setDefaultScenarioThreadCount(2)

  getScenarioClassData(SampleScenario).put(SampleScenarioClassInitializer.Data.SCENARIO_CLASS, 
    "scenario class")

  scenario = addScenario(SampleScenario)
  scenario.getScenarioData().put(SampleScenario.Data.SCENARIO_TYPE,
    SampleScenario.ScenarioType.T1)

  scenario = addScenario(SampleScenario)
  scenario.getScenarioData().put(SampleScenario.Data.SCENARIO_TYPE, 
    SampleScenario.ScenarioType.T2)
}