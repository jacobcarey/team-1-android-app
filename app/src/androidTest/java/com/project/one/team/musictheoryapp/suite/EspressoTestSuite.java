package com.project.one.team.musictheoryapp.suite;

import com.project.one.team.musictheoryapp.BasicSelectActivityTest;
import com.project.one.team.musictheoryapp.MainActivityTest;
import com.project.one.team.musictheoryapp.SettingsActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for running all Espresso UI tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MainActivityTest.class, SettingsActivityTest.class,
        BasicSelectActivityTest.class})
public class EspressoTestSuite {}
