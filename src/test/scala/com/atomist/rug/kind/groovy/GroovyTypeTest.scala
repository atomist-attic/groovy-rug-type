package com.atomist.rug.kind.groovy


import com.atomist.rug.runtime.rugdsl.Evaluator
import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.mockito.Mockito._
import org.hamcrest.MatcherAssert._
import org.hamcrest.CoreMatchers._

/**
  * Created by justin on 1/13/17.
  */
class GroovyTypeTest extends FlatSpec with BeforeAndAfter {

  var mockEvaluator : Evaluator = _
  var groovyType : GroovyType = _

  before {

    mockEvaluator = mock(classOf[Evaluator])
    groovyType = new GroovyType(mockEvaluator)

  }

  "A GroovyType" should "describe itself accurately as \'Groovy source file\'" in {

    val expected = "Groovy source file"
    val groovyTypeDesc = groovyType.description

    assertThat(groovyTypeDesc, equalTo(expected))
  }
}
