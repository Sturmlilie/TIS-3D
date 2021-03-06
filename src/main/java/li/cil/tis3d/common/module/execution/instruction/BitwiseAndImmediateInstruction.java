package li.cil.tis3d.common.module.execution.instruction;

import li.cil.tis3d.common.module.execution.Machine;
import li.cil.tis3d.common.module.execution.MachineState;

public final class BitwiseAndImmediateInstruction implements Instruction {
    private final short value;

    public BitwiseAndImmediateInstruction(final short value) {
        this.value = value;
    }

    @Override
    public void step(final Machine machine) {
        final MachineState state = machine.getState();
        state.acc &= value;
        state.pc++;
    }

    @Override
    public String toString() {
        return BitwiseAndInstruction.NAME + " " + value;
    }
}
