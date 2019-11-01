package com.marcio.popularmoviesclean.state

open class StateMachine<T, E>(
    private val dispatcher: Dispatcher<T>,
    private val errorFactory: ErrorFactory<E>,
    protected var currentState: State<T, E> = State(State.Name.IDLE),
    private val listeners: MutableList<StateListener<T, E>> = mutableListOf()
) {
    fun addStateChangedListener(listener: StateListener<T, E>) {
        this.listeners.add(listener)
        listener.onStateChanged(currentState)
    }

    fun removeStateChangedListener(listener: StateListener<T, E>) {
        this.listeners.remove(listener)
    }

    fun loadNewState(function: () -> T) {
        if (currentState.name == State.Name.LOADING) {
            changeAndEmitState(currentState)
            return
        }

        changeAndEmitState(
            State(
                State.Name.LOADING,
                currentState.value
            )
        )

        dispatcher.dispatch(
            function,
            { value ->
                changeAndEmitState(
                    State(
                        State.Name.LOADED,
                        value
                    )
                )
            },
            { throwable ->
                changeAndEmitState(
                    State(
                        State.Name.ERROR,
                        currentState.value,
                        errorFactory.create(throwable)
                    )
                )
            }
        )
    }

    private fun changeAndEmitState(state: State<T, E>) {
        currentState = state
        for (listener in listeners) {
            listener.onStateChanged(currentState)
        }
    }
}