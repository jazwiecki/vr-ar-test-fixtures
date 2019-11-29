import copy
import os
import pytest
import time

from appium import webdriver
from appium.webdriver.extensions.android.nativekey import AndroidKey
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
            take_screenshot_and_logcat(wd, device_logger, calling_request)
            wd.quit()

        request.addfinalizer(finish)

        wd.implicitly_wait(10)
        return wd

    def test_no_move(self, driver):
        app_output_box = driver.find_element_by_id('planetInfoCard')
        app_output_text = app_output_box.text

        assert "The space in front of you hasn't been processed yet." == app_output_text

    def test_move(self, driver):
        ux_fragment = driver.find_element_by_id('ux_fragment')
        # actions = ActionChains(driver)
        # driver.press_keycode(57)
        while True:
            driver.long_press_keycode(AndroidKey.W, metastate=50)

