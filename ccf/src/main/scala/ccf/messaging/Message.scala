/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ccf.messaging

import ccf.tree.operation.TreeOperation

sealed abstract class Message
case class ConcurrentOperationMessage(val op: TreeOperation, val localMessage: Int, val expectedRemoteMessage: Int) extends Message
case class ErrorMessage(val reason: String) extends Message
case class ChannelShutdown(val reason: String) extends Message
