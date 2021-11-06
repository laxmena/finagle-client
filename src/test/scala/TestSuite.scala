package com.laxmena

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.laxmena.HelperUtil.{ObtainConfigReference}
import com.typesafe.config.{Config, ConfigFactory}
import com.laxmena.Constants

class TestSuite extends AnyFlatSpec with Matchers {
  behavior of "Configuration parameters module"
  // App Config Tests
  var config = ObtainConfigReference(Constants.CONFIG) match {
    case Some(value) => value.getConfig(Constants.CONFIG)
    case None => throw new RuntimeException("Cannot obtain reference to the Config data")
  }


  it should "Contain host configuration" in {
    assert(config.hasPath(Constants.HOST))
  }

  it should "contain port" in {
    assert(config.hasPath(Constants.PORT))
  }

  it should "contain content type" in {
    assert(config.hasPath(Constants.CONTENT_TYPE))
  }

  it should "contain accept type" in {
    assert(config.hasPath(Constants.ACCEPT))
  }

  it should "Contain route" in {
    assert(config.hasPath(Constants.ROUTE))
  }

}