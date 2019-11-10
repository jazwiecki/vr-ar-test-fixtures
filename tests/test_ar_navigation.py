import pytest
import os
import copy

from appium import webdriver
from helpers import take_screenshot_and_logcat, ANDROID_BASE_CAPS, EXECUTOR

class TestMove():
    PACKAGE = 'io.appium.android.apis'
    AR_APPLICATION = '.HelloSceneformActivity'


    @pytest.fixture(scope='function')
    def driver(self, request, device_logger):
        calling_request = request._pyfuncitem.name

        caps = copy.copy(ANDROID_BASE_CAPS)
        caps['appActivity'] = self.AR_APPLICATION

        wd = webdriver.Remote(
            command_executor=EXECUTOR,
            desired_capabilities=caps
        )

        def finish():
            take_screenshot_and_logcat(driver, device_logger, calling_request)
            driver.quit()

        request.addFinalizer(finish)

        wd.implicitly_wait(10)
        return wd

    def test_move_forward(self, driver):
        app_output_box = driver.find_element_by_id('planetInfoCard')
        app_output_text = app_output_box.text

        assert 'TK definite error' == app_output_text