package com.marcio.popularmoviesclean.state

import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.mockk.verifySequence
import org.junit.Test

class StateMachineTest {

    @Test
    fun `Given an idle state When state listener is added Then should emit idle state`() {
        val stateMachine = object : StateMachine<TestValue, TestError>(
            FakeDispatcher(),
            TestErrorFactory(),
            State(State.Name.IDLE),
            mutableListOf()
        ) {
            override fun start() {}
        }
        val listenerMock = mockk<StateListener<TestValue, TestError>>(relaxed = true)

        stateMachine.addStateChangedListener(listenerMock)

        verify {
            listenerMock.onStateChanged(State(State.Name.IDLE))
        }
    }

    @Test
    fun `Given an idle state When state listener is added and removed and state is changed to loaded Then should not emit changes to listener`() {
        val value = TestValue()
        val stateMachine = object : StateMachine<TestValue, TestError>(
            FakeDispatcher(),
            TestErrorFactory(),
            State(State.Name.IDLE),
            mutableListOf()
        ) {
            override fun start() {}
        }
        val listenerMock = mockk<StateListener<TestValue, TestError>>(relaxed = true)

        stateMachine.addStateChangedListener(listenerMock)
        stateMachine.removeStateChangedListener(listenerMock)
        stateMachine.loadNewState {
            value
        }

        verify(exactly = 0) {
            listenerMock.onStateChanged(State(State.Name.LOADING))
            listenerMock.onStateChanged(State(State.Name.LOADED, value))
        }
    }

    @Test
    fun `Given an idle state and an added state listener When a second listener is added Then should emit idle state only to second`() {
        val stateMachine = object : StateMachine<TestValue, TestError>(
            FakeDispatcher(),
            TestErrorFactory(),
            State(State.Name.IDLE),
            mutableListOf()
        ) {
            override fun start() {}
        }
        val firstListenerMock = mockk<StateListener<TestValue, TestError>>(relaxed = true)
        val secondListenerMock = mockk<StateListener<TestValue, TestError>>(relaxed = true)

        stateMachine.addStateChangedListener(firstListenerMock)
        stateMachine.addStateChangedListener(secondListenerMock)

        verify {
            secondListenerMock.onStateChanged(State(State.Name.IDLE))
        }

        verify(exactly = 1) {
            firstListenerMock.onStateChanged(State(State.Name.IDLE))
        }
    }

    @Test
    fun `Given an idle state When two different state listeners are added and the first is removed and state is changed to loaded Then should emit loaded state only to second`() {
        val value = TestValue()
        val stateMachine = object : StateMachine<TestValue, TestError>(
            FakeDispatcher(),
            TestErrorFactory(),
            State(State.Name.IDLE),
            mutableListOf()
        ) {
            override fun start() {}
        }
        val firstListenerMock = mockk<StateListener<TestValue, TestError>>(relaxed = true)
        val secondListenerMock = mockk<StateListener<TestValue, TestError>>(relaxed = true)

        stateMachine.addStateChangedListener(firstListenerMock)
        stateMachine.addStateChangedListener(secondListenerMock)
        stateMachine.removeStateChangedListener(firstListenerMock)
        stateMachine.loadNewState { value }

        verify(exactly = 0) {
            firstListenerMock.onStateChanged(State(State.Name.LOADING))
            firstListenerMock.onStateChanged(State(State.Name.LOADED, value))
        }

        verifyOrder {
            secondListenerMock.onStateChanged(State(State.Name.LOADING))
            secondListenerMock.onStateChanged(State(State.Name.LOADED, value))
        }
    }

    @Test
    fun `Given an idle state When load new state is called Then current state should change to loaded after loading`() {
        val value = TestValue()
        val stateMachine = object : StateMachine<TestValue, TestError>(
            FakeDispatcher(),
            TestErrorFactory(),
            State(State.Name.IDLE),
            mutableListOf()
        ) {
            override fun start() {}
        }
        val mockListener = mockk<StateListener<TestValue, TestError>>(relaxed = true)

        stateMachine.addStateChangedListener(mockListener)
        stateMachine.loadNewState { value }

        verifyOrder {
            mockListener.onStateChanged(State(State.Name.LOADING))
            mockListener.onStateChanged(State(State.Name.LOADED, value))
        }
    }

    @Test
    fun `Given an loaded state When load new state is called And an error occurs Then current state should change to error`() {
        val value = TestValue()
        val error = TestError()
        val stateMachine = object : StateMachine<TestValue, TestError>(
            FakeDispatcher(),
            TestErrorFactory(error),
            State(State.Name.LOADED, value),
            mutableListOf()
        ) {
            override fun start() {}
        }
        val mockListener = mockk<StateListener<TestValue, TestError>>(relaxed = true)

        stateMachine.addStateChangedListener(mockListener)
        stateMachine.loadNewState {
            throw IllegalStateException()
        }

        verify {
            mockListener.onStateChanged(State(State.Name.ERROR, value, error))
        }
    }

    @Test
    fun `Given a loading state When load new state is called Then current state should change to loading`() {
        val stateMachine = object : StateMachine<TestValue, TestError>(
            FakeDispatcher(),
            TestErrorFactory(),
            State(State.Name.LOADING),
            mutableListOf()
        ) {
            override fun start() {}
        }
        val mockListener = mockk<StateListener<TestValue, TestError>>(relaxed = true)

        stateMachine.addStateChangedListener(mockListener)
        stateMachine.loadNewState { TestValue() }

        verifySequence {
            mockListener.onStateChanged(State(State.Name.LOADING))
            mockListener.onStateChanged(State(State.Name.LOADING))
        }
    }

    @Test
    fun `Given an idle state When two different state listeners are added and state is changed to loaded Then should emit loaded state to both listeners`() {
        val value = TestValue()
        val stateMachine = object : StateMachine<TestValue, TestError>(
            FakeDispatcher(),
            TestErrorFactory(),
            State(State.Name.IDLE),
            mutableListOf()
        ) {
            override fun start() {}
        }
        val firstListenerMock = mockk<StateListener<TestValue, TestError>>(relaxed = true)
        val secondListenerMock = mockk<StateListener<TestValue, TestError>>(relaxed = true)

        stateMachine.addStateChangedListener(firstListenerMock)
        stateMachine.addStateChangedListener(secondListenerMock)
        stateMachine.loadNewState { value }

        verifyOrder {
            firstListenerMock.onStateChanged(State(State.Name.LOADING))
            firstListenerMock.onStateChanged(State(State.Name.LOADED, value))
        }

        verifyOrder {
            secondListenerMock.onStateChanged(State(State.Name.LOADING))
            secondListenerMock.onStateChanged(State(State.Name.LOADED, value))
        }
    }
}

class TestValue

class TestError

class TestErrorFactory(private val error: TestError? = null) : ErrorFactory<TestError> {
    override fun create(throwable: Throwable) = error!!
}
