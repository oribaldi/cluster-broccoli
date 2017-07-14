package de.frosner.broccoli.controllers

import de.frosner.broccoli.models.{Account, Role, UserAccount}
import de.frosner.broccoli.services.SecurityService
import org.specs2.matcher.MatchResult
import org.specs2.matcher.MatchersImplicits._
import play.api.mvc.{Action, AnyContent, Result}
import play.api.test._
import play.api.test.Helpers._
import jp.t2v.lab.play2.auth.test.Helpers._
import org.mockito.Mockito._

import scala.concurrent.Future

trait AuthUtils extends ServiceMocks {

  def testWithAllAuths[T <: AuthConfigImpl](account: Account)(controller: SecurityService => T)(
      action: T => Action[AnyContent])(requestModifier: FakeRequest[_] => FakeRequest[_])(
      matcher: (T, Future[Result]) => MatchResult[_]): MatchResult[_] = {
    val confAuthController = controller(withAuthConf(mock(classOf[SecurityService]), List(account)))
    val confAuthRequest = requestModifier(
      FakeRequest().withLoggedIn(confAuthController)(account.name)
    ).asInstanceOf[FakeRequest[AnyContent]]
    val confAuthResult = action(confAuthController).apply(confAuthRequest)
    val confAuthMatcher = matcher(confAuthController, confAuthResult)

    val noAuthController = controller(withAuthNone(mock(classOf[SecurityService])))
    val noAuthRequest = requestModifier(FakeRequest()).asInstanceOf[FakeRequest[AnyContent]]
    val confAuthNoLoginResult = action(confAuthController).apply(noAuthRequest)
    val confAuthNoLoginMatcher = status(confAuthNoLoginResult) === 403

    confAuthMatcher and confAuthNoLoginMatcher
  }

  def testWithAllAuths[T <: AuthConfigImpl](controller: SecurityService => T)(action: T => Action[AnyContent])(
      requestModifier: FakeRequest[_] => FakeRequest[_])(
      matcher: (T, Future[Result]) => MatchResult[_]): MatchResult[_] = {
    val account = UserAccount("user", "pass", ".*", Role.Administrator)
    val noAuthController = controller(withAuthNone(mock(classOf[SecurityService])))
    val noAuthRequest = requestModifier(FakeRequest()).asInstanceOf[FakeRequest[AnyContent]]
    val noAuthResult = action(noAuthController).apply(noAuthRequest)
    val noAuthMatcher = matcher(noAuthController, noAuthResult)

    val authMatchers = testWithAllAuths(account)(controller)(action)(requestModifier)(matcher)

    noAuthMatcher and authMatchers
  }

}
