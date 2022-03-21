package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;

public interface CharacterAdapter extends Consumer<CharacterInfo> {

}
