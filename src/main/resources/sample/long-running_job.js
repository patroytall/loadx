with (new JavaImporter(org.roy.loadx.sample)) {

  setDefaultScenarioIterationCount(100)
  setDefaultScenarioRunIterationCount(100)
  setDefaultScenarioThreadCount(10)

  addScenario(LongRunningScenario)
}