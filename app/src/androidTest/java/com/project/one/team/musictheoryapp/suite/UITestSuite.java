package com.project.one.team.musictheoryapp.suite;

import com.project.one.team.musictheoryapp.AdvancedSelectActivityTest;
import com.project.one.team.musictheoryapp.BasicSelectActivityTest;
import com.project.one.team.musictheoryapp.ContentActivityTest;
import com.project.one.team.musictheoryapp.IntermediateSelectActivityTest;
import com.project.one.team.musictheoryapp.MainActivityTest;
import com.project.one.team.musictheoryapp.QuizActivityTest;
import com.project.one.team.musictheoryapp.SettingsActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>Test Suite for running all Espresso UI tests.</p>
 *
 * @author Team One
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MainActivityTest.class, SettingsActivityTest.class,
        BasicSelectActivityTest.class, IntermediateSelectActivityTest.class, AdvancedSelectActivityTest.class,
        ContentActivityTest.class, QuizActivityTest.class})
public class UITestSuite {}
