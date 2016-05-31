with (new JavaImporter(org.roy.loadx.pub.sample)) {

  setDefaultScenarioIterationCount(100)
  setDefaultScenarioRunIterationCount(100)
  setDefaultScenarioThreadCount(10)

  addScenario(LongRunningScenario)
}